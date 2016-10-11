package com.jiumeng.movieheaven2.provider;

import com.jiumeng.movieheaven2.entity.MovieEntity;

import java.util.Comparator;


/**
 * 由于每次加载的电影集合,都采用多线程并发加载，所以导致影片不是按更新时间排序的
 * 所以自定义集合排序类实现Comparator接口， 重写集合排序方法，按电影更新时间排序
 * Created by jiumeng on 2016/4/25.
 */

public class MovieListSort implements Comparator {
    public int compare(Object o1, Object o2) {
        MovieEntity movie = (MovieEntity) o1;
        MovieEntity movie2 = (MovieEntity) o2;
        String time1 = movie.updatetime.replaceAll("-", "");
        String time2 = movie2.updatetime.replaceAll("-", "");
        return time2.compareTo(time1);
    }
}
