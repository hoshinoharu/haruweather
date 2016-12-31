package com.aphidmobile.flip.haruweather.dao;

import android.util.Log;

import com.haru.tools.Constants;

import org.litepal.crud.DataSupport;
import org.litepal.util.LogUtil;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class Province extends DataSupport {

    private int id ;
    private String name ;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provinceCode=" + provinceCode +
                '}';
    }

    public static boolean saveFromJson(String json){
        Province[] provinces = Constants.GSON.fromJson(json, Province[].class) ;
        if(provinces.length>0){
            for(Province province : provinces){
                province.setProvinceCode(province.getId());
                if(!province.save()){
                    break;
                }
            }
            return true ;
        }
        return false ;
    }
}
