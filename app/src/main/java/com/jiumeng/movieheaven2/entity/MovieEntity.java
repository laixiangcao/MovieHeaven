package com.jiumeng.movieheaven2.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * movieInfo
 * Created by jiumeng on 2016/4/13.
 */
public class MovieEntity implements Serializable {
    private static final long serialVersionUID = 2L;
    //热门&最新：    url id name minNmae  日期 评分 类型 年代  国家  语言 字幕 上映日期
    //分类：         url id name minName  日期 评分 类型 导演  主演
    public String url;//电影连接
    public String id;//电影连接md5
    public String name;//电影带细节全称
    public String minName;//电影名称
    public String updatetime;//更新时间
    public ArrayList<String> jpgList;//影片截图
    public ArrayList<String> downlist;//下载连接
    public String introduction;//影片简介
    public String country;//国家
    public String subtitle;//字幕
    public String language;//语言
    public String category;//类别
    public String years;//年代
    public String play_time;//片长
    public String grade;//ibdm评分/豆辩评分
    public String director;//导演
    public String starring;//主演

    @Override
    public String toString() {
        return "MovieEntity{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", minName='" + minName + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", jpgList=" + jpgList +
                ", downlist=" + downlist +
                ", introduction='" + introduction + '\'' +
                ", country='" + country + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", language='" + language + '\'' +
                ", category='" + category + '\'' +
                ", years='" + years + '\'' +
                ", play_time='" + play_time + '\'' +
                ", grade='" + grade + '\'' +
                ", director='" + director + '\'' +
                ", starring='" + starring + '\'' +
                ", filesize='" + filesize + '\'' +
                '}';
    }

    public String filesize;//文件大小
}
