package com.jiumeng.movieheaven2.provider;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiumeng on 2016/10/4.
 */

public class ProcessHomeUrl {
    public static ArrayList<ArrayList<String>> getHomeUrl(String text) {
        ArrayList<ArrayList<String>> homeUrlList = new ArrayList<>();
        String substring = null;
        int idx1 = text.indexOf("新片精品");
        int idx2 = text.indexOf("<!--电视剧-->");
        try {
            substring = text.substring(idx1, idx2);
        } catch (Exception e) {
            return null;
        }
        String[] subUrls = substring.split("title_all");

        for (String subString : subUrls) {
            String teg="(href='/).*html";
            Pattern p = Pattern.compile(teg);
            Matcher m = p.matcher(subString);
            ArrayList<String> arrayList = new ArrayList<>();
            while (m.find()) {
                arrayList.add("http://www.dy2018.com" + m.group().replace("href='",""));
            }
            homeUrlList.add(arrayList);
        }
        return homeUrlList;
    }
}
