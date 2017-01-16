package com.jiumeng.movieheaven.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * 1. 重新定义初始化方法 getlayoutId()、initViews()、initData();
 * 2. ButterKnife绑定
 * Created by jiumeng on 2016/10/24.
 */

public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mRootView);
            initUI();
            initData();
        }
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initUI();

    protected abstract void initData();

}
