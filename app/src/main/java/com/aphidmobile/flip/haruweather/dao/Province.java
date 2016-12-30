package com.aphidmobile.flip.haruweather.dao;

import org.litepal.crud.DataSupport;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class Province extends DataSupport {

    private int id ;
    private String provinceName ;
    private int provinceCode ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id='" + id + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode=" + provinceCode +
                '}';
    }
}
