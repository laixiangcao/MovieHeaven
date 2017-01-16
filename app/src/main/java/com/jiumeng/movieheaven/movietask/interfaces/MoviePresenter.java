package com.jiumeng.movieheaven.movietask.interfaces;

import rx.Subscription;

/**
 * Created by jiumeng on 2016/12/30.
 */

public interface MoviePresenter {
    void subscribe();

    void unSubscribe();

    void addSubscription(Subscription subscription);
}
