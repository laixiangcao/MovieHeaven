package com.jiumeng.movieheaven.entity;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * movieInfo
 * Created by jiumeng on 2016/4/13.
 */
public class MovieEntity extends BmobObject implements Serializable {
    private static final long serialVersionUID = 2L;
    public String url;//电影连接
    public String id;//电影连接md5
    public String name;//电影带细节全称
    public String minName;//电影名称
    public String updatetime;//更新时间
    public ArrayList<String> jpgList;//影片截图
    public ArrayList<String> downlist;//下载连接
    public String introduction;//影片简介
    public String country;//国家
    public String category;//类别
    public String years;//年代
    public String play_time;//片长
    public String grade;//ibdm评分/豆辩评分
    public String director;//导演
    public String starring;//主演

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinName(String minName) {
        this.minName = minName;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setJpgList(ArrayList<String> jpgList) {
        this.jpgList = jpgList;
    }

    public void setDownlist(ArrayList<String> downlist) {
        this.downlist = downlist;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }


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
                ", category='" + category + '\'' +
                ", years='" + years + '\'' +
                ", play_time='" + play_time + '\'' +
                ", grade='" + grade + '\'' +
                ", director='" + director + '\'' +
                ", starring='" + starring + '\'' +
                '}';
    }


}
