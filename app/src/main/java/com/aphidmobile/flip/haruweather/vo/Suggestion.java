package com.aphidmobile.flip.haruweather.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort ;

    @SerializedName("cw")
    public CarWash carWash ;

    public Sport sport ;


    public class Comfort {
        @SerializedName("txt")
        public String info ;

        @Override
        public String toString() {
            return "Comfort{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }

    public class CarWash{
        @SerializedName("txt")
        public String info ;

        @Override
        public String toString() {
            return "CarWash{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }

    public class Sport {
        @SerializedName("txt")
        public String info ;

        @Override
        public String toString() {
            return "Sport{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "carWash=" + carWash +
                ", comfort=" + comfort +
                ", sport=" + sport +
                '}';
    }
}
