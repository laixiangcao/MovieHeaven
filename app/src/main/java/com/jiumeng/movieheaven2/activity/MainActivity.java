package com.jiumeng.movieheaven2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.bean.MainTab;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    private LayoutInflater mLayoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //实例化布局对象
        mLayoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        initTabs();
        mTabHost.getTabWidget().setShowDividers(0);
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
    }
    private void initTabs() {

        MainTab[] tabs = MainTab.values();
        for (MainTab mainTab : tabs) {
            //实例化一个Tab标签view
            View indicator = mLayoutInflater.inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);

            //实例化一个Tab标签并设置图标和文字内容
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            icon.setImageDrawable(getResources().getDrawable(mainTab.getResIcon()));
            title.setText(getString(mainTab.getResName()));

            //设置标签view给tab
            tab.setIndicator(indicator);

            //添加一个标签
            mTabHost.addTab(tab, mainTab.getClz(), null);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        //mTabHost标签切换
    }
}
