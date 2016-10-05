package com.jiumeng.movieheaven2.fragment.tabhost;


import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.bean.ViewPageInfo;
import com.jiumeng.movieheaven2.fragment.base.BaseViewPagerFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.ClassifyVpFragment;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类页面
 * Created by jiumeng on 2016/9/24.
 */
public class ClassifyFragment extends BaseViewPagerFragment {

    @Override
    protected List<ViewPageInfo> onSetupTabAdapter() {
        String[] strings = getContext().getResources().getStringArray(R.array.classify_indicator_names);
        ArrayList<ViewPageInfo> viewPageInfos = new ArrayList<>();
        viewPageInfos.add(new ViewPageInfo(strings[0], "action", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_ACTION)));
        viewPageInfos.add(new ViewPageInfo(strings[1], "terror", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_TERROR)));
        viewPageInfos.add(new ViewPageInfo(strings[2], "science_fiction", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_SCIENCE_FICTION)));
        viewPageInfos.add(new ViewPageInfo(strings[3], "comedy", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_COMEDY)));
        viewPageInfos.add(new ViewPageInfo(strings[4], "scenario", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_SCENARIO)));
        viewPageInfos.add(new ViewPageInfo(strings[5], "affection", ClassifyVpFragment.class, getBundle(NetWorkApi.MVOIETYPE_AFFECTION)));
        return viewPageInfos;
    }
}
