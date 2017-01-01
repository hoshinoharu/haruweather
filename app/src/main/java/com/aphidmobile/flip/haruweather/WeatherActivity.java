package com.aphidmobile.flip.haruweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.haruweather.vo.Forecast;
import com.aphidmobile.flip.haruweather.vo.Weather;
import com.bumptech.glide.Glide;
import com.haru.tools.HttpTool;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout ;

    private TextView titleCity ;

    private TextView titleUpdateTime ;

    private TextView degreeText ;

    private TextView weatherInfoText ;

    private LinearLayout forecastLayout ;

    private TextView aqiText ;

    private TextView pm25Text ;

    private TextView comfortText ;

    private TextView carWashText ;

    private TextView sportText ;

    private ImageView bingPicImg ;

    private SwipeRefreshLayout swipeRefresh ;

    private DrawerLayout drawerLayout ;

    private Button navButton ;

    private ViewGroup chooseAreaContent ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        this.init();
    }

    private void init() {
        this.weatherLayout = (ScrollView) this.findViewById(R.id.weather_layout);
        this.titleCity = (TextView) this.findViewById(R.id.title_city);
        this.titleUpdateTime = (TextView) this.findViewById(R.id.title_update_time);
        this.degreeText = (TextView) this.findViewById(R.id.degree_text);
        this.weatherInfoText = (TextView) this.findViewById(R.id.weather_info_text);
        this.forecastLayout = (LinearLayout) this.findViewById(R.id.forecast_layout);
        this.aqiText = (TextView) this.findViewById(R.id.aqi_text);
        this.pm25Text = (TextView) this.findViewById(R.id.pm25_text);
        this.comfortText = (TextView) this.findViewById(R.id.comfort_text);
        this.carWashText = (TextView) this.findViewById(R.id.car_wash_text);
        this.sportText = (TextView) this.findViewById(R.id.sport_text);
        this.bingPicImg = (ImageView) this.findViewById(R.id.bing_pic_img) ;
        this.swipeRefresh = (SwipeRefreshLayout) this.findViewById(R.id.swip_refresh);
        this.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        this.drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        this.navButton = (Button) this.findViewById(R.id.nav_button);


        this.navButton.setOnClickListener((view)->{
            drawerLayout.openDrawer(GravityCompat.START);
        });


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        String weatherString = prefs.getString("weather", null) ;
        if(weatherString != null){
            Weather weather = Weather.getFromJson(weatherString) ;
            showWeatherInfo(weather);
        }else {

            String weatherId = getIntent().getStringExtra("weather_id") ;
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        this.swipeRefresh.setOnRefreshListener(()->{
            requestWeather(getIntent().getStringExtra("weather_id"));
        });
        String bingPic = prefs.getString("bing_pic", null) ;
        if(bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg) ;
        }else{
            loadBingPic();
        }

        this.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(chooseAreaContent == null){
                    chooseAreaContent = (ViewGroup) drawerView.findViewById(R.id.choose_area_content);
                }
                swipeRefresh.setX(drawerView.getWidth()+drawerView.getX());
                chooseAreaContent.setX((1-slideOffset)*400);
                Log.e("TAG", chooseAreaContent.getX()+"") ;
            }
        });


    }

    private void loadBingPic() {
        String url = "http://guolin.tech/api/bing_pic" ;
        HttpTool.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string() ;
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit() ;
                editor.putString("bing_pic", bingPic) ;
                editor.apply();
                runOnUiThread(()->{
                    Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg) ;
                });
            }
        });
    }

    public void requestWeather(String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=b98bc8d9d82d49389a5f2e9751fba978&lang=zh_CN" ;
        HttpTool.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(()->{
                    Toast.makeText(WeatherActivity.this, R.string.getWeatherFail, Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string() ;
                Log.e("TAG", "response:" + json) ;
                Weather weather = Weather.getFromJson(json);
                runOnUiThread(()->{
                    if(weather != null && "ok".equals(weather.status)){
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit() ;
                        editor.putString("weather", json) ;
                        editor.apply();
                        showWeatherInfo(weather);
                    }else{
                        Toast.makeText(WeatherActivity.this, R.string.getWeatherFail, Toast.LENGTH_SHORT).show();
                    }
                    swipeRefresh.setRefreshing(false);

                });
            }
        });
    }

    public void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName ;
        String updateTime = weather.basic.update.updateTime.split(" ")[1] ;
        String degree = weather.now.temperature + getString(R.string.tmpunit);
        String weatherInfo = weather.now.more.info ;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        Log.e("TAG", weather.toString()) ;
        for(Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate( R.layout.forecast_item, forecastLayout, false) ;
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi != null) {
            this.aqiText.setText(weather.aqi.city.aqi);
            this.pm25Text.setText(weather.aqi.city.pm25);
        }else{
            this.aqiText.setText(R.string.nul);
            this.pm25Text.setText(R.string.nul);
        }

        String comfort = getString(R.string.comfortInfo)  + weather.suggestion.comfort.info ;
        String carWash = getString(R.string.caeWashInfo) + weather.suggestion.carWash.info ;
        String sport = getString(R.string.sportInfo) + weather.suggestion.sport.info ;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    public static void start(Context context, String weatherId){
        Intent intent = new Intent(context, WeatherActivity.class) ;
        intent.putExtra("weather_id", weatherId) ;
        context.startActivity(intent);
    }


    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }
}
