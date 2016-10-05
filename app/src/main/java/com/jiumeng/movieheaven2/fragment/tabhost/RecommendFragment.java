package com.jiumeng.movieheaven2.fragment.tabhost;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.adapter.ViewPageFragmentAdapter;
import com.jiumeng.movieheaven2.bean.ViewPageInfo;
import com.jiumeng.movieheaven2.fragment.base.BaseLoadFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.AmericaVpFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.ClassicsVpFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.HotestVpFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.NewestVpFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.RecommendVpFragment;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.views.LoadingPage;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jiumeng on 2016/9/24.
 */
public class RecommendFragment extends BaseLoadFragment {

    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewPagerTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected View onCreateSuccessView() {
        View mRootView = mLayoutInflater.inflate(R.layout.fragment_recommend, null);
        ButterKnife.bind(this, mRootView);

        ViewPageFragmentAdapter mPageAdapter = new ViewPageFragmentAdapter(getContext(), getChildFragmentManager(), onSetupTabAdapter());
        mViewPager.setAdapter(mPageAdapter);
        mViewPagerTab.setViewPager(mViewPager);

        return mRootView;
    }

    @Override
    protected void initPageData(boolean isFirstLoad) {
        super.loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);

    }


    private List<ViewPageInfo> onSetupTabAdapter() {
        String[] strings = getContext().getResources().getStringArray(R.array.recommend_indicator_names);
        ArrayList<ViewPageInfo> viewPageInfos = new ArrayList<>();
        viewPageInfos.add(new ViewPageInfo(strings[0], "recommend", RecommendVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_ACTION)));
        viewPageInfos.add(new ViewPageInfo(strings[1], "newest", NewestVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_NEWEST)));
        viewPageInfos.add(new ViewPageInfo(strings[2], "hotest", HotestVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_HOTTEST)));
        viewPageInfos.add(new ViewPageInfo(strings[3], "classics", ClassicsVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_SCIENCE_FICTION)));
        viewPageInfos.add(new ViewPageInfo(strings[4], "america", AmericaVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_COMEDY)));
        return viewPageInfos;
    }

    private Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("movieType",type);
        return bundle;
    }


    @OnClick({R.id.et_search, R.id.iv_voice_search})
    public void search(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                Toast.makeText(getContext(), "et_search", Toast.LENGTH_SHORT).show();
            case R.id.iv_voice_search:
                Toast.makeText(getContext(), "iv_voice_search", Toast.LENGTH_SHORT).show();
        }
    }
}
