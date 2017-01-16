package com.jiumeng.movieheaven.utils;

import android.util.Log;

/**
 * Created by jiumeng on 2016/11/24.
 */

public class LogUtils {
    private static final int ERROR = 5;
    private static final int WARN = 4;
    private static final int INFO = 3;
    private static final int DEBUG = 2;
    private static final int VERBOSE = 1;
    private static final int INTERCEPT_LEVEL = VERBOSE;

    private static final String TAG = "jiumeng";

    public static void e(String tag, String msg) {
        if (INTERCEPT_LEVEL <= ERROR)
            Log.e(tag, msg);

    }

    public static void w(String tag, String msg) {
        if (INTERCEPT_LEVEL <= WARN)
            Log.e(tag, msg);

    }

    public static void i(String tag, String msg) {
        if (INTERCEPT_LEVEL <= INFO)
            Log.e(tag, msg);

    }


    public static void d(String tag, String msg) {
        if (INTERCEPT_LEVEL <= DEBUG)
            Log.e(tag, msg);

    }

    public static void v(String tag, String msg) {
        if (INTERCEPT_LEVEL <= VERBOSE)
            Log.e(tag, msg);

    }


    //============使用默认的Tag============
    public static void e(String msg) {
        if (INTERCEPT_LEVEL <= ERROR)
            Log.e(TAG, msg);

    }

    public static void w(String msg) {
        if (INTERCEPT_LEVEL <= WARN)
            Log.e(TAG, msg);

    }

    public static void i(String msg) {
        if (INTERCEPT_LEVEL <= INFO)
            Log.e(TAG, msg);

    }

    public static void d(String msg) {
        if (INTERCEPT_LEVEL <= DEBUG)
            Log.e(TAG, msg);

    }

    public static void v(String msg) {
        if (INTERCEPT_LEVEL <= VERBOSE)
            Log.e(TAG, msg);

    }


}
