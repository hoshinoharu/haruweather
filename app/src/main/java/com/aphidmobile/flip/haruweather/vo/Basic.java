package com.aphidmobile.flip.haruweather.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class Basic  {
    @SerializedName("city")
    public String cityName ;

    @SerializedName("id")
    public String weatherId ;

    public Update update ;
    public class Update{
        @SerializedName("loc")
        public String updateTime;

        @Override
        public String toString() {
            return "Update{" +
                    "updateTime='" + updateTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Basic{" +
                "cityName='" + cityName + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", update=" + update +
                '}';
    }
}
