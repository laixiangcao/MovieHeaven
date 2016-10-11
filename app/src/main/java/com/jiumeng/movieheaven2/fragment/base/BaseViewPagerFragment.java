package com.jiumeng.movieheaven2.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.adapter.ViewPageFragmentAdapter;
import com.jiumeng.movieheaven2.entity.ViewPageInfoEntity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ViewPager页面基类
 * Created by jiumeng on 2016/9/25.
 */

public abstract class BaseViewPagerFragment extends Fragment {

    private View mRootView;
    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewPagerTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private ViewPageFragmentAdapter mPageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_base_viewpager, null);
            ButterKnife.bind(this, mRootView);
            mPageAdapter = new ViewPageFragmentAdapter(getContext(), getChildFragmentManager(), onSetupTabAdapter());
            mViewPager.setAdapter(mPageAdapter);
            mViewPagerTab.setViewPager(mViewPager);
            setScreenPageLimit();
        }
        return mRootView;
    }

    /**
     * 添加tab标签的
     */
    protected abstract List<ViewPageInfoEntity> onSetupTabAdapter();


    /**
     * 子类覆写该方法，实现设置缓存tabsub个数
     */
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(0);
    }

    protected Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("movieType",type);
        return bundle;
    }
}
