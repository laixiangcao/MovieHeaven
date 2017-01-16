package com.jiumeng.movieheaven.utils;


import com.jiumeng.movieheaven.http.ApiService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import okhttp3.ResponseBody;

/**
 * Created by jiumeng on 2016/10/14.
 */

public class TextUtils {

    public static String url2Id(String url, boolean isCompress) {
        String suffix = "";
        if (isCompress) {
            suffix = ApiService.JPG_SUFFIX;
        }
        url = url.replaceAll("http://www.dy2018.com/", "");
        return ApiService.BASE_SERVICE_URL + textToMD5(url) + ".jpg" + suffix;
    }

    public static String id2Url(String id, boolean isCompress) {
        String suffix = "";
        if (isCompress) {
            suffix = ApiService.JPG_SUFFIX;
        }
        System.out.println(ApiService.BASE_SERVICE_URL + File.separator + id + ".jpg" + suffix);
        return ApiService.BASE_SERVICE_URL + File.separator + id + ".jpg" + suffix;
    }

    /**
     * @param text
     * @return true 文本为空 false 文本不为空
     */
    public static boolean isEmpty(String text) {
        return !(text != null && !text.equals("") && text.length() > 0);
    }

    public static String bytes2String(ResponseBody responseBody) {
        String text = null;
        try {
            text = new String(responseBody.bytes(), "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtils.writeTextToSD(text, "test.txt");
        return text;
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

    public static String byteArray2String(byte[] bytes) {
        try {
            return new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
