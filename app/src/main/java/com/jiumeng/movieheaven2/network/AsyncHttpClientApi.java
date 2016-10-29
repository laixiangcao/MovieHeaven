package com.jiumeng.movieheaven2.network;


import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class AsyncHttpClientApi {


    public static void requestData(String url, Object obj, MyStringCallback callback) {
        OkHttpUtils
                .get()
                .url(url)
                .tag(obj)
                .build()
                .execute(callback);
    }
}
