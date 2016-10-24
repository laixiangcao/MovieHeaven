package com.jiumeng.movieheaven2.network;


import com.jiumeng.movieheaven2.utils.MyTextUtils;

import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.type;

/**
 * 实现请求网络数据的封装
 * 加载网络数据时，只需要传入：分类 页面索引 接受请求结果的handler
 * Created by Administrator on 2016/7/27 0027.
 */
public class NetWorkApi {

    /**
     * 经典电影类型
     */
    public static final int MOVIETYPE_CLASSICS = 16;
    /**
     * 影院电影类型
     */
    public static final int MOVIETYPE_CINEMAMOVIE = 14;
    /**
     * 最新电影类型
     */
    public static final int MOVIETYPE_NEWEST = 15;
    /**
     * 最热电影类型
     */
    public static final int MOVIETYPE_HOTTEST = 2;
    /**
     * 剧情
     */
    public static final int MOVIETYPE_SCENARIO = 3;
    /**
     * 喜剧
     */
    public static final int MOVIETYPE_COMEDY = 4;
    /**
     * 动作
     */
    public static final int MOVIETYPE_ACTION = 5;
    /**
     * 爱情
     */
    public static final int MOVIETYPE_AFFECTION = 6;
    /**
     * 科幻
     */
    public static final int MOVIETYPE_SCIENCE_FICTION = 7;
    /**
     * 动画
     */
    public static final int MOVIETYPE_ANIMATION = 8;
    /**
     * 悬疑
     */
    public static final int MOVIETYPE_SUSPENSE = 9;
    /**
     * 惊悚
     */
    public static final int MOVIETYPE_PANIC = 10;
    /**
     * 战争
     */
    public static final int MOVIETYPE_WAR = 11;
    /**
     * 犯罪
     */
    public static final int MOVIETYPE_CRIME = 12;
    /**
     * 恐怖
     */
    public static final int MOVIETYPE_TERROR = 13;


    public final static String MYHOST = "http://oaydggmwi.bkt.clouddn.com";//主连接
    public final static String HOST = "http://www.dy2018.com";//主连接
    public final static String APK_DOWNLOAD = MYHOST + "/com.jiumeng.movieheaven.apk";

    /**
     * 获取指定类型和指定页数的电影集合
     *
     * @param catalog 电影类型
     * @param page    页
     * @param handler 接收请求结果的handler
     */
    public static void getPageInfoFromNet(int catalog, int page, Object tag, MyStringCallback handler) {
        String path = "";
        switch (catalog) {
            case MOVIETYPE_NEWEST:
                if (page == 1) {
                    path = HOST + "/html/gndy/dyzz/index.html";
                } else {
                    path = HOST + "/html/gndy/dyzz/index_" + page + ".html";
                }
                break;
            case MOVIETYPE_HOTTEST:
                path = HOST + MyTextUtils.page2Url("/html/bikan/", page);
                break;
            case MOVIETYPE_CLASSICS:
                path = HOST + MyTextUtils.page2Url("/html/gndy/jddyy/", page);
                break;
            case MOVIETYPE_SCENARIO:
                path = HOST + MyTextUtils.page2Url("/0/", page);
                break;
            case MOVIETYPE_COMEDY:
                path = HOST + MyTextUtils.page2Url("/1/", page);
                break;
            case MOVIETYPE_ACTION:
                path = HOST + MyTextUtils.page2Url("/2/", page);
                break;
            case MOVIETYPE_AFFECTION:
                path = HOST + MyTextUtils.page2Url("/3/", page);
                break;
            case MOVIETYPE_SCIENCE_FICTION:
                path = HOST + MyTextUtils.page2Url("/4/", page);
                break;
            case MOVIETYPE_ANIMATION:
                path = HOST + MyTextUtils.page2Url("/5/", page);
                break;
            case MOVIETYPE_SUSPENSE:
                path = HOST + MyTextUtils.page2Url("/6/", page);
                break;
            case MOVIETYPE_PANIC:
                path = HOST + MyTextUtils.page2Url("/7/", page);
                break;
            case MOVIETYPE_WAR:
                path = HOST + MyTextUtils.page2Url("/14/", page);
                break;
            case MOVIETYPE_CRIME:
                path = HOST + MyTextUtils.page2Url("/5/", page);
                break;
            case MOVIETYPE_TERROR:
                path = HOST + MyTextUtils.page2Url("/8/", page);
                break;
            case MOVIETYPE_CINEMAMOVIE:
                path = MYHOST + "/CinemaMovie/CinemaMovieList" + page + ".json";
                break;
        }
        AsyncHttpClientApi.requestData(path, tag, handler);
    }

    public static void getMovieDetailInfo(String url, Object tag, MyStringCallback handler) {
        AsyncHttpClientApi.requestData(url, tag, handler);
    }

    public static boolean urlIsAvailable(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
