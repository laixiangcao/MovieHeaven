package com.jiumeng.movieheaven2.fragment.tabhost;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.adapter.ViewPageFragmentAdapter;
import com.jiumeng.movieheaven2.entity.ViewPageInfoEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseLoadFragment;
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


    private List<ViewPageInfoEntity> onSetupTabAdapter() {
        String[] strings = getContext().getResources().getStringArray(R.array.recommend_indicator_names);
        ArrayList<ViewPageInfoEntity> viewPageInfos = new ArrayList<>();
        viewPageInfos.add(new ViewPageInfoEntity(strings[0], "recommend", RecommendVpFragment.class, getBundle(NetWorkApi.MOVIETYPE_ACTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[1], "newest", NewestVpFragment.class, getBundle(NetWorkApi.MOVIETYPE_NEWEST)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[2], "hotest", HotestVpFragment.class, getBundle(NetWorkApi.MOVIETYPE_HOTTEST)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[3], "classics", ClassicsVpFragment.class, getBundle(NetWorkApi.MOVIETYPE_CLASSICS)));
        return viewPageInfos;
    }

    private Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("movieType",type);
        return bundle;
    }
}
