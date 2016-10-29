package com.jiumeng.movieheaven2.fragment.tabhost;


import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.ViewPageInfoEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseViewPagerFragment;
import com.jiumeng.movieheaven2.fragment.viewpager.ClassifyVpFragment;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类页面
 * Created by jiumeng on 2016/9/24.
 */
public class ClassifyFragment extends BaseViewPagerFragment {

    @Override
    protected List<ViewPageInfoEntity> onSetupTabAdapter() {
        String[] strings = UIUtils.getStringArray(R.array.classify_indicator_names);
        ArrayList<ViewPageInfoEntity> viewPageInfos = new ArrayList<>();
        //添加分类电影界面：动作 恐怖 科幻 喜剧 剧情 爱情 动画 悬疑 惊悚 战争 犯罪
        viewPageInfos.add(new ViewPageInfoEntity(strings[0], "action", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_ACTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[1], "terror", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_TERROR)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[2], "science_fiction", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_SCIENCE_FICTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[3], "comedy", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_COMEDY)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[4], "scenario", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_SCENARIO)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[5], "affection", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_AFFECTION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[6], "animation", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_ANIMATION)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[7], "suspense", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_SUSPENSE)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[8], "panic", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_PANIC)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[9], "war", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_WAR)));
        viewPageInfos.add(new ViewPageInfoEntity(strings[10], "crime", ClassifyVpFragment.class, getBundle(NetWorkApi.MOVIE_TYPE_CRIME)));
        return viewPageInfos;
    }
}
