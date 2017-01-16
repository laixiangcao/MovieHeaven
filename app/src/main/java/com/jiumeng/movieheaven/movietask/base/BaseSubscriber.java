package com.jiumeng.movieheaven.movietask.base;



import com.jiumeng.movieheaven.movietask.interfaces.MovieView;
import com.jiumeng.movieheaven.utils.NetUtils;
import com.jiumeng.movieheaven.utils.ToastUtils;
import com.jiumeng.movieheaven.utils.UIUtils;

import rx.Subscriber;

/**
 * Created by jiumeng on 2016/12/5.
 */

public abstract class BaseSubscriber<V extends MovieView,T> extends Subscriber<T> {

    private final V view;

    public BaseSubscriber(V view) {
        this.view = view;
    }

    @Override
    public void onStart() {


        if (NetUtils.isConnected(UIUtils.getContext())) {
            ToastUtils.showShort("当前网络不可用，请检查网络情况");
            // **一定要主动调用下面这一句**
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
        //关闭进度条
        view.showLoading(false);
    }

    @Override
    public void onError(Throwable e) {
        //关闭进度条
        view.showLoading(false);
        ToastUtils.showShort(e.getLocalizedMessage());
    }
}
