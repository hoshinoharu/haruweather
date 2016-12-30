package com.aphidmobile.flip.haruweather.dao;

import org.litepal.crud.DataSupport;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class City extends DataSupport {
    private int id ;
    private String cityName ;
    private int cityCode ;

    private int provinceId ;

    @Override
    public String toString() {
        return "City{" +
                "cityCode=" + cityCode +
                ", id=" + id +
                ", cityName='" + cityName + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
}
