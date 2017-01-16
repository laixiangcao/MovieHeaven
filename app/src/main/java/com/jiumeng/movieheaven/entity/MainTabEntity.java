package com.jiumeng.movieheaven.entity;


import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.ui.fragment.tabhost.ClassifyFragment;
import com.jiumeng.movieheaven.ui.fragment.tabhost.DiscoverFragment;
import com.jiumeng.movieheaven.ui.fragment.tabhost.MyInfoFragment;
import com.jiumeng.movieheaven.ui.fragment.tabhost.RecommendFragment;

public enum MainTabEntity {

    NEWS(0, R.string.main_tab__name_recommend, R.drawable.tab_icon_new, RecommendFragment.class),

    TWEET(1, R.string.main_tab__name_classify, R.drawable.tab_icon_tweet, ClassifyFragment.class),

    EXPLORE(2, R.string.main_tab__name_discover, R.drawable.tab_icon_explore, DiscoverFragment.class),

    ME(3, R.string.main_tab__name_my, R.drawable.tab_icon_me, MyInfoFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    MainTabEntity(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
