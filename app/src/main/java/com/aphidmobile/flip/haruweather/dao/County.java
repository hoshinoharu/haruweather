package com.aphidmobile.flip.haruweather.dao;

import android.util.Log;

import com.haru.tools.Constants;

import org.litepal.crud.DataSupport;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class County extends DataSupport {
    private int id ;
    private String name ;
    private String weather_id ;
    private int cityId ;

    @Override
    public String toString() {
        return "County{" +
                "cityId=" + cityId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", weather_id='" + weather_id + '\'' +
                '}';
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    public static boolean saveFromJson(String json, int cityId){
        County[] counties = Constants.GSON.fromJson(json, County[].class) ;
        if(counties.length>0){
            for(County county : counties){
                county.setCityId(cityId);
                if(!county.save()){
                    break;
                }
            }
            return true ;
        }
        return false ;
    }
}
