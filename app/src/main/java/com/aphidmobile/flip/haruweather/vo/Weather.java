package com.aphidmobile.flip.haruweather.vo;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.haru.tools.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 星野悠 on 2016/12/31.
 */

public class Weather {

    public String status ;
    public Basic basic ;
    public AQI aqi ;
    public Now now ;
    public Suggestion suggestion ;

    @SerializedName("daily_forecast")
    public Forecast[] forecastList;

    public static Weather getFromJson(String json){
        Log.e("TAG", "JSON:" + json);
        try {
            JSONObject jsonObject = new JSONObject(json) ;
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5") ;
            String weatherContent = jsonArray.getJSONObject(0).toString() ;
            return Constants.GSON.fromJson(weatherContent, Weather.class) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "aqi=" + aqi +
                ", status='" + status + '\'' +
                ", basic=" + basic +
                ", now=" + now +
                ", suggestion=" + suggestion +
                ", forecastList=" + forecastList +
                '}';
    }
}
