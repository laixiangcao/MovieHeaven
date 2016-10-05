package com.jiumeng.movieheaven2.provider;

import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiumeng on 2016/9/29.
 */

public class DataTools {
    //读取本地缓存
    public static List<MovieDao> getCacheData(int type) {
        return (List<MovieDao>) PrefUtils.readObject("CacheData:" + type);
    }

    //判断缓存是否失效
    public static boolean isCacheInvalid(int type) {
        long invalidTime = PrefUtils.getLong("CacheData:" + type + ":CacheTime");
        long time = System.currentTimeMillis() - invalidTime;
        return time > 10800 * 1000;
    }

    /**
     * 校验集合数据
     * @param obj 需要校验的集合对象
     * @return 返回页面显示状态
     */
    // 对网络返回数据的合法性进行校验
    public static LoadingPage.ResultState checkData(Object obj) {
        if (obj != null) {
            if (obj instanceof ArrayList) {// 判断是否是集合
                ArrayList list = (ArrayList) obj;
                if (list.isEmpty()) {
                    return LoadingPage.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
