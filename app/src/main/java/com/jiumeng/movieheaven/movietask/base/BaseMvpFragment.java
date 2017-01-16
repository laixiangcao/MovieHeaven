package com.jiumeng.movieheaven.movietask.base;


import com.jiumeng.movieheaven.ui.base.BaseFragment;

/**
 * Created by jiumeng on 2016/12/30.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {


    protected P mPresenter;

    @Override
    protected void initData() {
        mPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.unSubscribe();
        }
    }
}
