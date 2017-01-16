package com.jiumeng.movieheaven.movietask.base;


import com.jiumeng.movieheaven.movietask.interfaces.MoviePresenter;
import com.jiumeng.movieheaven.movietask.interfaces.MovieView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends MovieView> implements MoviePresenter {

    protected V view;
    protected int movieType;

    private CompositeSubscription mCompositeSubscription;


    public BasePresenter(int movieType, V mvpView) {
        this.movieType = movieType;
        this.view = mvpView;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        this.view = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    private void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        if (subscription != null)
            mCompositeSubscription.add(subscription);
    }

}
