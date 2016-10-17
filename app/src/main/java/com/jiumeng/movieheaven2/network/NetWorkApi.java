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
     * 影院电影类型
     */
    public static final int MVOIETYPE_CINEMAMOVIE = 14;
    /**
     * 最新电影类型
     */
    public static final int MVOIETYPE_NEWEST = 15;
    /**
     * 最热电影类型
     */
    public static final int MVOIETYPE_HOTTEST = 2;
    /**
     * 剧情
     */
    public static final int MVOIETYPE_SCENARIO = 3;
    /**
     * 喜剧
     */
    public static final int MVOIETYPE_COMEDY = 4;
    /**
     * 动作
     */
    public static final int MVOIETYPE_ACTION = 5;
    /**
     * 爱情
     */
    public static final int MVOIETYPE_AFFECTION = 6;
    /**
     * 科幻
     */
    public static final int MVOIETYPE_SCIENCE_FICTION = 7;
    /**
     * 动画
     */
    public static final int MVOIETYPE_ANIMATION = 8;
    /**
     * 悬疑
     */
    public static final int MVOIETYPE_SUSPENSE = 9;
    /**
     * 惊悚
     */
    public static final int MVOIETYPE_PANIC = 10;
    /**
     * 战争
     */
    public static final int MVOIETYPE_WAR = 11;
    /**
     * 犯罪
     */
    public static final int MVOIETYPE_CRIME = 12;
    /**
     * 恐怖
     */
    public static final int MVOIETYPE_TERROR = 13;


    public final static String MYHOST = "http://oaydggmwi.bkt.clouddn.com";//主连接
    public final static String HOST = "http://www.dy2018.com";//主连接

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
            case MVOIETYPE_NEWEST:
                if (page == 1) {
                    path = HOST + "/html/gndy/dyzz/index.html";
                } else {
                    path = HOST + "/html/gndy/dyzz/index_" + page + ".html";
                }
                break;
            case MVOIETYPE_HOTTEST:
                path = HOST + MyTextUtils.page2Url("/html/bikan/", page);
                break;
            case MVOIETYPE_SCENARIO:
                path = HOST + MyTextUtils.page2Url("/0/", page);
                break;
            case MVOIETYPE_COMEDY:
                path = HOST + MyTextUtils.page2Url("/1/", page);
                break;
            case MVOIETYPE_ACTION:
                path = HOST + MyTextUtils.page2Url("/2/", page);
                break;
            case MVOIETYPE_AFFECTION:
                path = HOST + MyTextUtils.page2Url("/3/", page);
                break;
            case MVOIETYPE_SCIENCE_FICTION:
                path = HOST + MyTextUtils.page2Url("/4/", page);
                break;
            case MVOIETYPE_ANIMATION:
                path = HOST + MyTextUtils.page2Url("/5/", page);
                break;
            case MVOIETYPE_SUSPENSE:
                path = HOST + MyTextUtils.page2Url("/6/", page);
                break;
            case MVOIETYPE_PANIC:
                path = HOST + MyTextUtils.page2Url("/7/", page);
                break;
            case MVOIETYPE_WAR:
                path = HOST + MyTextUtils.page2Url("/14/", page);
                break;
            case MVOIETYPE_CRIME:
                path = HOST + MyTextUtils.page2Url("/5/", page);
                break;
            case MVOIETYPE_TERROR:
                path = HOST + MyTextUtils.page2Url("/8/", page);
                break;
            case MVOIETYPE_CINEMAMOVIE:
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
