package com.jiumeng.movieheaven.global;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiumeng on 2016/11/17.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final String CRASH_STORAGE_LOCATION = "/sdcard/movieheaven/crash/";
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".crash";
    //CrashHandler实例
    private volatile static CrashHandler instance = null;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> crashInfos = new HashMap<>();


    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultException;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
        //获取应用默认的UncaughtException处理
        mDefaultException = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handlerException(e) && mDefaultException != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultException.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                Log.e(TAG, "error:" + e1);
            }
            //退出程序
            android.os.Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return
     */
    private boolean handlerException(Throwable ex) {
        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 获取crash目录下的全部异常日志信息
     *
     * @param context
     * @return
     */
    private String[] getCrashReportFiles(Context context) {
        File file = new File(CRASH_STORAGE_LOCATION);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return file.list(filenameFilter);
    }

    /**
     * 保存日志文件
     *
     * @param ex
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : crashInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            //用于格式化日期,作为日志文件名的一部分
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".crash";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/movie/heavencrash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;

    }

    /**
     * 收集设备参数信息
     * @param mContext
     */
    private void collectDeviceInfo(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            //获取用户版本信息
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                crashInfos.put("versionName", versionName);
                crashInfos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "an error occured when collect package info", e);
        }

        //获取手机参数信息
        String brand = Build.BRAND;//手机品牌
        String model = Build.MODEL;//手机型号
        String release = Build.VERSION.RELEASE;//手机系统版本
        String vresionSdk = Build.VERSION.SDK_INT + "";//sdk版本

        crashInfos.put("brand", brand);
        crashInfos.put("model", model);
        crashInfos.put("release", release);
        crashInfos.put("vresionSdk", vresionSdk);

//        Field[] declaredFields = Build.class.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            try {
//                Log.e(TAG, declaredField.getName() + declaredField.get(null).toString());
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//
//            }
//        }

    }
}
