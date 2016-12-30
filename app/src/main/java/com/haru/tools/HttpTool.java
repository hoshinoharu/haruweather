package com.haru.tools;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 星野悠 on 2016/12/30.
 */

public class HttpTool {
    public static void sendOkHttpRequest(String url, Callback callback){
        OkHttpClient client = new OkHttpClient() ;
        Request request = new Request.Builder().url(url).build() ;
        client.newCall(request).enqueue(callback);
    }
}
