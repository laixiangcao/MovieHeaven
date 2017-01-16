package com.jiumeng.movieheaven.ui.fragment.viewpager;

import com.jiumeng.movieheaven.ui.base.BaseFragment;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.utils.LogUtils;

/**
 * 推荐页面的viewpager的 最新电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class HotestVpFragment  extends BaseFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_support_multiple_layout;
    }

    @Override
    protected void initUI() {
        LogUtils.i("页面初始化："+this.getClass().getName());
    }

    @Override
    protected void initData() {

    }
}