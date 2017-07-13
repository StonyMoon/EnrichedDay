package com.meo.stonymoon.enrichedday.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/*
发送请求的封装方法

 */


public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);

    }

}
