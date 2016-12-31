package com.aphidmobile.flip.haruweather.components;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.haruweather.R;
import com.aphidmobile.flip.haruweather.WeatherActivity;
import com.aphidmobile.flip.haruweather.dao.City;
import com.aphidmobile.flip.haruweather.dao.County;
import com.aphidmobile.flip.haruweather.dao.Province;
import com.haru.tools.HttpTool;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class ChooseAreaFragment extends Fragment {

    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private BaseAdapter adapter;
    private List<String> dataList = new ArrayList<>();

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private int currentLevel;

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        this.titleText = (TextView) view.findViewById(R.id.title_text);
        this.backButton = (Button) view.findViewById(R.id.back_button);
        this.listView = (ListView) view.findViewById(R.id.list_view);
        this.adapter = new BaseAdapter(){
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public Object getItem(int i) {
                return dataList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view == null){
                    view = new TextView(getActivity()) ;
                }
                ((TextView)view).setText(dataList.get(i));
                return view ;
            }
        };
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(i);
                    queryCounties();
                }else if(currentLevel == LEVEL_COUNTY){
                    WeatherActivity.start(getActivity(), countyList.get(i).getWeather_id());
                    getActivity().finish();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    private void queryCounties() {
        titleText.setText(selectedCity.getName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class) ;
        if(countyList.size() > 0){
            dataList.clear();
            for(County county : countyList){
                Log.e("TAG", "county" + county.getName());
                dataList.add(county.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY ;
        }else {
            int provinceCode = selectedProvince.getProvinceCode() ;
            int cityCode = selectedCity.getCityCode() ;
            String url = "http://guolin.tech/api/china/" + provinceCode+"/"+cityCode;
            queryFromServer(url, "county");
        }
    }

    private void queryFromServer(String url, final String type) {
        showProgressDialog();
        HttpTool.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(()->{
                    closeProgressDialog();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string() ;
                boolean result = false ;
                if("province".equals(type)){
                    result = Province.saveFromJson(json) ;
                }else if("city".equals(type)){
                    result = City.saveFromJson(json, selectedProvince.getId()) ;
                }else if("county".equals(type)){
                    result = County.saveFromJson(json, selectedCity.getId()) ;
                }

                if(result){
                    getActivity().runOnUiThread(()->{
                        closeProgressDialog();
                        if("province".equals(type)){
                            queryProvinces();
                        }else if("city".equals(type)){
                            queryCities();
                        }else if("county".equals(type)){
                            queryCounties();
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity()) ;
            progressDialog.setTitle(R.string.loading);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void queryCities() {
        titleText.setText(selectedProvince.getName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceId = selectedProvince.getProvinceCode();
            String url = "http://guolin.tech/api/china/" + provinceId ;
            queryFromServer(url, "city");
        }
    }

    private void queryProvinces() {
        titleText.setText(R.string.china);
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String url = "http://guolin.tech/api/china/";
            queryFromServer(url, "province");
        }
    }


}
