package com.jiumeng.movieheaven.utils;

/**
 * Created by jiumeng on 2016/10/23.
 */

import android.os.Looper;
import android.text.format.Formatter;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by zhaoyong on 2016/6/21.
 * Glide缓存工具类
 */
public class ImageCatchUtil {

    private static ImageCatchUtil inst;

    public static ImageCatchUtil getInstance() {
        if (inst == null) {
            inst = new ImageCatchUtil();
        }
        return inst;
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize() {
        File photoCacheDir = Glide.getPhotoCacheDir(UIUtils.getContext());
        long totalSpace = getFileSizes(photoCacheDir);
        return Formatter.formatFileSize(UIUtils.getContext(), totalSpace);
    }

    private boolean isEmptyCache() {
        File photoCacheDir = Glide.getPhotoCacheDir(UIUtils.getContext());
        int length = photoCacheDir.listFiles().length;
        return length == 0;
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final ClearCacheListener listener) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(UIUtils.getContext()).clearDiskCache();
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (ImageCatchUtil.getInstance().isEmptyCache()) {
                                    ToastUtils.showShort("缓存已清理");
                                    listener.update(getCacheSize());
                                }
                            }
                        });
                    }
                }).start();
            } else {
                Glide.get(UIUtils.getContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ClearCacheListener {
        void update(String cache);
    }


    /***
     * 获取文件夹大小
     ***/
    private long getFileSizes(File f) {
        long size = 0;
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                size = size + getFileSizes(file);
            } else {
                size = size + file.length();
            }
        }
        return size;
    }

}
