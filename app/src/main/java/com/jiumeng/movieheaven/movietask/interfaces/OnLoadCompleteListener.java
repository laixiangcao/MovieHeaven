package com.jiumeng.movieheaven.movietask.interfaces;

/**
 * Created by jiumeng on 2017/1/5.
 */

public interface OnLoadCompleteListener<T> {
    void onSuccess(T t);
    void onError(Throwable e);
}
