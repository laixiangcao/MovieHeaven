package com.jiumeng.movieheaven2.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.facebook.drawee.backends.pipeline.Fresco;

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
        Fresco.initialize(this);
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
