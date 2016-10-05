package com.jiumeng.movieheaven2.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiumeng.movieheaven2.views.LoadingPage;

/**
 * 能展示加载状态的页面（加载成功、失败、无网络、加载中、无数据）
 * Created by jiumeng on 2016/9/24.
 */

public abstract class BaseLoadFragment extends Fragment{

    private LoadingPage mLoadingPage;
    protected LayoutInflater mLayoutInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        if (mLoadingPage==null){
            mLoadingPage = new LoadingPage(getContext()) {
                @Override
                public View onCreateSuccessView() {
                    return BaseLoadFragment.this.onCreateSuccessView();
                }
                @Override
                public void requestNetData() {
                    BaseLoadFragment.this.initPageData(true);
                }
            };
        }
        return mLoadingPage;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //初试化加载页面
        if (mLoadingPage!=null){
            mLoadingPage.loadData();
        }
    }

    /**
     * 网络数据加载成功后需要加载的布局界面
     * @return 网络数据加载成功后需要加载的布局界面
     */
    protected abstract View onCreateSuccessView();

    /**
     * 初始化数据（缓存的数据/网络加载数据）
     */
    protected abstract void initPageData(boolean isFirstLoad);


    /**
     * 加载数据完成后 调用此方法 通知界面更新
     * @param resultState
     */
    protected void loadDataComplete(LoadingPage.ResultState resultState){
        mLoadingPage.loadDataComplete(resultState);
    }

}
