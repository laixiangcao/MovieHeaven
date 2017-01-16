package com.jiumeng.movieheaven.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiumeng on 2016/10/25.
 */

public class MyFavoriteEntity extends BmobObject {
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String movieName;
    private String movieUrl;

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }


}
