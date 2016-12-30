package com.aphidmobile.flip.haruweather.dao;

import android.util.Log;

import com.haru.tools.Constants;

import org.litepal.crud.DataSupport;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class City extends DataSupport {
    private int id ;
    private String name ;
    private int cityCode ;

    private int provinceId ;



    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityCode=" + cityCode +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }

    public static boolean saveFromJson(String json, int provinceId){

        City[] cities = Constants.GSON.fromJson(json, City[].class) ;
        if(cities.length>0){
            for(City city : cities){
                Log.e("TAG", "" + city.getName()) ;
                city.setProvinceId(provinceId);
                if(!city.save()){
                    break;
                }
            }
            return true ;
        }
        return false ;
    }
}
