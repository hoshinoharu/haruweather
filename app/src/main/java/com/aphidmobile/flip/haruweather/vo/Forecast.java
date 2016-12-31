package com.aphidmobile.flip.haruweather.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class Forecast {
    public String date ;

    @SerializedName("tmp")
    public Temperature temperature ;

    @SerializedName("cond")
    public More more ;

    public class Temperature {
        public String max ;

        public String min ;

        @Override
        public String toString() {
            return "Temperature{" +
                    "max='" + max + '\'' +
                    ", min='" + min + '\'' +
                    '}';
        }
    }

    public class More{
        @SerializedName("txt_d")
        public String info ;

        @Override
        public String toString() {
            return "More{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", more=" + more +
                '}';
    }
}
