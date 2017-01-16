package com.jiumeng.movieheaven.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;
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

        //初始化Bmob后端云
        Bmob.initialize(this, "900c26e77f87f1d7153973028a431d4a", "jiumeng");
        ShareSDK.initSDK(this);
        //初始化okhttp
        initOkHttpClient();
        //初始化自定义异常捕获
//        CrashHandler.getInstance().init(context);
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
