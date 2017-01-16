package com.jiumeng.movieheaven.ui.fragment.tabhost;


import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jiumeng.movieheaven.ui.base.BaseFragment;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.ui.adapter.ViewPageFragmentAdapter;
import com.jiumeng.movieheaven.entity.ViewPageInfoEntity;
import com.jiumeng.movieheaven.ui.fragment.viewpager.RecommendVpFragment;
import com.jiumeng.movieheaven.global.GlobalAttribute;
import com.jiumeng.movieheaven.http.ApiService;
import com.jiumeng.movieheaven.utils.UIUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 分类页面
 * Created by jiumeng on 2016/9/24.
 */
public class ClassifyFragment extends BaseFragment {
    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewPagerTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_host_classify;
    }

    @Override
    protected void initUI() {
        ViewPageFragmentAdapter mPageAdapter = new ViewPageFragmentAdapter(getContext(), getChildFragmentManager(), onSetupTabAdapter());
        mViewPager.setAdapter(mPageAdapter);
        mViewPagerTab.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(0);//设置缓存页面个数为0
    }

    @Override
    protected void initData() {
        onSetupTabAdapter();
    }

    private List<ViewPageInfoEntity> onSetupTabAdapter() {
        String[] strings = UIUtils.getStringArray(R.array.classify_indicator_names);
        ArrayList<ViewPageInfoEntity> viewPageInfos = new ArrayList<>();
        //添加分类电影界面：动作 恐怖 科幻 喜剧 剧情 爱情 动画 悬疑 惊悚 战争 犯罪
        viewPageInfos.add(new ViewPageInfoEntity(strings[0], "action", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_ACTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[1], "terror", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_TERROR)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[2], "science_fiction", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_SCIENCE_FICTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[3], "comedy", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_COMEDY)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[4], "scenario", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_SCENARIO)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[5], "affection", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_AFFECTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[6], "animation", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_ANIMATION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[7], "suspense", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_SUSPENSE)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[8], "panic", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_PANIC)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[9], "war", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_WAR)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[10], "crime", RecommendVpFragment.class, getBundle(ApiService.MOVIE_TYPE_CRIME)));
        return viewPageInfos;
    }

    private Bundle getBundle(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalAttribute.MOVIE_CATEGORY, type);
        return bundle;
    }
}
