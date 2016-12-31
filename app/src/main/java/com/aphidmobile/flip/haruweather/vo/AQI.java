package com.aphidmobile.flip.haruweather.vo;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class AQI {
    public AQICity city ;

    public class AQICity {
        public String aqi ;
        public String pm25 ;

        @Override
        public String toString() {
            return "AQICity{" +
                    "aqi='" + aqi + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AQI{" +
                "city=" + city +
                '}';
    }
}
