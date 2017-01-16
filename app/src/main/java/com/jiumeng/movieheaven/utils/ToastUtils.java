package com.jiumeng.movieheaven.utils;


import android.widget.Toast;

/**
 * Created by jiumeng on 2016/12/15.
 */

public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    private static boolean isShow = true;


    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow)
            Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow)
            Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
