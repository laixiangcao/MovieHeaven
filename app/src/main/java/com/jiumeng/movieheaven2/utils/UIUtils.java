package com.jiumeng.movieheaven2.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jiumeng.movieheaven2.global.MyApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by jiumeng on 2016/6/15.
 */
public class UIUtils {

    //获取上下文
    public static Context getContext() {

        return MyApplication.getContext();
    }

    //获取一个handler
    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    //获取主线程ID
    public static int getMainThreadId() {
        return MyApplication.getMainThreadId();
    }


    // /////////////////加载资源文件 ///////////////////////////

    //获取String
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //获取StringArray
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取Drawable
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    //获取Color
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    //获取Color状态选择器
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    //获取尺寸
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    // /////////////////dip和px转换//////////////////////////
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    // /////////////////加载布局文件//////////////////////////
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    //判断是否运行在主线程
    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        if (Process.myTid() == getMainThreadId()) {
            return true;
        } else {
            return false;
        }
    }

    //让任务运行在主线程
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

//    /**
//     * 隐藏输入法
//     */
//    public static void hideSoftKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
//        if (isOpen) {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
//        }
//    }

    /**
     * @return 0:没有网络 1：移动网络 2：wifi网络
     */
    public static int getNetworkType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UIUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return 0;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE) {
            return 1;
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return 2;
        }
        return 0;
    }


    /**
     * 将字符串转换成MD5
     *
     * @param Text 需要转换的字符串
     * @return md5值
     */
    public static String textToMD5(String Text) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(Text.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteArray2String(byte[] bytes){
        try {
            return new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null) return;
        View mFocusView = view;

        Context context = view.getContext();
        if (context != null && context instanceof Activity) {
            Activity activity = ((Activity) context);
            mFocusView = activity.getCurrentFocus();
        }
        if (mFocusView == null) return;
        mFocusView.clearFocus();
        InputMethodManager manager = (InputMethodManager) mFocusView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String getVersionName() {
        try {
            return UIUtils
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(UIUtils.getContext().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }

}
