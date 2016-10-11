package com.jiumeng.movieheaven2.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 自定义application 进行全局初始化
 * Created by jiumeng on 2016/6/15.
 */
public class MyApplication extends Application {

    private static Handler handler;
    private static int mainThreadId;
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        mainThreadId = Process.myTid();
        handler = new Handler();

        initOkHttpClient();
    }

    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
