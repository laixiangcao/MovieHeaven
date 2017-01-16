package com.jiumeng.movieheaven.ui.fragment.tabhost;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.entity.ViewPageInfoEntity;
import com.jiumeng.movieheaven.global.GlobalAttribute;
import com.jiumeng.movieheaven.http.ApiService;
import com.jiumeng.movieheaven.ui.activity.MovieSearchActivity;
import com.jiumeng.movieheaven.ui.adapter.ViewPageFragmentAdapter;
import com.jiumeng.movieheaven.ui.base.BaseFragment;
import com.jiumeng.movieheaven.ui.fragment.viewpager.ClassicsVpFragment;
import com.jiumeng.movieheaven.ui.fragment.viewpager.HotestVpFragment;
import com.jiumeng.movieheaven.ui.fragment.viewpager.NewestVpFragment;
import com.jiumeng.movieheaven.ui.fragment.viewpager.RecommendVpFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by jiumeng on 2016/9/24.
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewPagerTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_host_recommend;
    }

    @Override
    protected void initUI() {
        ViewPageFragmentAdapter mPageAdapter = new ViewPageFragmentAdapter(getContext(), getChildFragmentManager(), onSetupTabAdapter());
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(1);
        mViewPagerTab.setViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        onSetupTabAdapter();
    }

    private List<ViewPageInfoEntity> onSetupTabAdapter() {
        String[] strings = getContext().getResources().getStringArray(R.array.recommend_indicator_names);
        ArrayList<ViewPageInfoEntity> viewPageInfos = new ArrayList<>();
        viewPageInfos.add(new ViewPageInfoEntity(strings[0], "recommend", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_RECOMMEND)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[1], "newest", NewestVpFragment.class, getBundle(ApiService.MOVIE_TYPE_NEWEST)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[2], "hotest", HotestVpFragment.class, getBundle(ApiService.MOVIE_TYPE_HOTTEST)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[3], "classics", ClassicsVpFragment.class, getBundle(ApiService.MOVIE_TYPE_CLASSICS)));
        return viewPageInfos;
    }

    private Bundle getBundle(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalAttribute.MOVIE_CATEGORY, type);
        return bundle;
    }

    @OnClick(R.id.et_search)
    public void onClick() {
        Intent intent = new Intent(getActivity(), MovieSearchActivity.class);
        startActivity(intent);
    }
}
