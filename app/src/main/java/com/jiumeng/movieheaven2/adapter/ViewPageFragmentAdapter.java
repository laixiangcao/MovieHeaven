package com.jiumeng.movieheaven2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import com.jiumeng.movieheaven2.entity.ViewPageInfoEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewPageInfoEntity> mTabs = new ArrayList<>();
    private Map<String, Fragment> mFragments = new ArrayMap<>();

    public ViewPageFragmentAdapter(Context context,FragmentManager fm, List<ViewPageInfoEntity> viewPageInfoData) {
        super(fm);
        mContext = context;
        mTabs=viewPageInfoData;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        ViewPageInfoEntity info = mTabs.get(position);
        Fragment fragment = mFragments.get(info.tag);
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext,info.clss.getName());
            // 避免重复创建而进行缓存
            fragment.setArguments(info.mBundle);
            mFragments.put(info.tag, fragment);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).title;
    }
    public void updateFragment(List<ViewPageInfoEntity> info) {
        if (info == null) {
            return;
        }
        mTabs=info;
        notifyDataSetChanged();
    }
}