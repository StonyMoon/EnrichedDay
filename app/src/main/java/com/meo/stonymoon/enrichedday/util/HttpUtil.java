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

    public static void sendBilibiliRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Referer", "http://www.bilibili.com/video/av17008/?from=search")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                .build();
        client.newCall(request).enqueue(callback);

    }




}
