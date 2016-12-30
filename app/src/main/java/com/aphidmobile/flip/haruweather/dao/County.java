package com.aphidmobile.flip.haruweather.dao;

import com.haru.tools.Constants;

import org.litepal.crud.DataSupport;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class County extends DataSupport {
    private int id ;
    private String countyName ;
    private String weatherId ;
    private int cityId ;

    @Override
    public String toString() {
        return "County{" +
                "cityId=" + cityId +
                ", id=" + id +
                ", countyName='" + countyName + '\'' +
                ", weatherId='" + weatherId + '\'' +
                '}';
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
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
