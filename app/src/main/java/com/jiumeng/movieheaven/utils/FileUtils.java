package com.jiumeng.movieheaven.utils;

import android.os.Environment;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jiumeng on 2016/11/22.
 */

public class FileUtils {

    public static final String JPG = ".jpg";

    /**
     * 电影天堂主文件夹目录
     */
    private static final String FOLDER_NAME = "movieheaven";
    /**
     * 存放电影图片目录
     */
    private static final String MOVIE_PIC = "movie_picture";
    /**
     * 下载失败的目录
     */
    private static final String FAILURE_URL = "failure";
    /**
     * 下载图片失败的文件名
     */
    public static final String FAILURE_PIC_URL_NAME = "picUrls.txt";
    /**
     * 文件夹路径
     */
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME + File.separator;


    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /***
     * 获取文件夹内存大小
     ***/
    public static long getFileSizes(File f) {
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

    /**
     * 获取SD卡的容量
     *
     * @return
     */
    public static String getSDCardAllSize() {
        if (isSDCardEnable()) {
            long totalSize = Environment.getExternalStorageDirectory().getTotalSpace();
            return Formatter.formatFileSize(UIUtils.getContext(), totalSize);   //总外置存储设备大小
        }
        return null;
    }

    /**
     * 获取SD卡的剩余容量
     *
     * @return
     */
    public static String GetSDCardAvailableSize() {
        if (isSDCardEnable()) {
            long availableSize = Environment.getExternalStorageDirectory().getUsableSpace();
            return Formatter.formatFileSize(UIUtils.getContext(), availableSize);   //剩余外置存储设备大小
        }
        return null;
    }


    /**
     * 写入文本到SD卡
     *
     * @param movieContent 文本内容
     * @param fileName     文件名
     */
    public static void writeTextToSD(String movieContent, String fileName) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            File path = new File(FILE_PATH);
            File file = new File(path, fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(movieContent.getBytes());
            fos.close();
        } catch (Exception e) {
            LogUtils.e("写入文本文件出错");
            e.printStackTrace();
        }
    }

    /**
     * 写入文件到sd卡
     *
     * @param bytes    该文件的字节数组
     * @param fileName 文件名
     */
    public static void writeFileToSD(byte[] bytes, String fileName) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            File path = new File(FILE_PATH + MOVIE_PIC);
            File file = new File(path, fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            LogUtils.e("写入文件出错");
            e.printStackTrace();
        }


    }

    public static void delectFile(String fileName) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return;
        }
        File file = new File(FILE_PATH + MOVIE_PIC + fileName);
        if (file.exists()) {
            LogUtils.i("文件删除：" + file.delete());
        }
    }

    /**
     * 清除无效文件
     */
    public static void deleteFile(File file) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return;
        }
        file.delete();
    }


    /**
     * 获取文件夹的所有file
     *
     * @return
     */
    public static File[] getFilePath(File file) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return null;
        }
        return file.listFiles();
    }

    public static ArrayList<String> getFileContent(File file) {
        if (!isSDCardEnable()) {
            LogUtils.e("SD card is not avaiable/writeable right now.");
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            ArrayList<String> urls = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                urls.add(line);
            }
            reader.close();
            fis.close();
            return urls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
