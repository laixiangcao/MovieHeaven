package com.jiumeng.movieheaven2.network;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

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
