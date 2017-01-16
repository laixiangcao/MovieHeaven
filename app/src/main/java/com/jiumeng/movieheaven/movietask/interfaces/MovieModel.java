package com.jiumeng.movieheaven.movietask.interfaces;


import rx.Subscription;

/**
 * Created by jiumeng on 2017/1/5.
 */

public interface MovieModel<T> {
    Subscription loadData(int page, OnLoadCompleteListener<T> listener);
}
