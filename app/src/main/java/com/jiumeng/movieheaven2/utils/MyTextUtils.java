package com.jiumeng.movieheaven2.utils;

import com.jiumeng.movieheaven2.network.NetWorkApi;

import static android.R.attr.id;
import static android.R.attr.path;

/**
 * Created by jiumeng on 2016/10/14.
 */

public class MyTextUtils {
    public static String id2Url(String id) {
        return NetWorkApi.MYHOST + "/image/" + id + ".jpg_movieheaven";
    }

    public static String page2Url(String type, int page) {
        String path;
        if (page == 1) {
            path = type;
        } else {
            path = type + "/index_" + page + ".html";
        }
        return path;
    }

    public static boolean isEmpty(String text) {
        return text != null && text.equals("") && text.length() > 0;
    }
}
