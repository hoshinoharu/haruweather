package com.aphidmobile.flip.haruweather.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature ;

    @SerializedName("cond")
    public  More more ;

    public class More {
        @SerializedName("txt")
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
        return "Now{" +
                "more=" + more +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
