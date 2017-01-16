package com.jiumeng.movieheaven.ui.activity;

import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.jiumeng.movieheaven.ui.base.BaseActivity;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.engine.AccountManager;
import com.jiumeng.movieheaven.entity.MainTabEntity;
import com.jiumeng.movieheaven.global.GlobalAttribute;
import com.jiumeng.movieheaven.utils.PrefUtils;
import com.jiumeng.movieheaven.utils.ToastUtils;

import butterknife.BindView;
import cn.bmob.v3.update.BmobUpdateAgent;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private LayoutInflater mLayoutInflater;


    @Override
    protected void initViews() {


        //实例化布局对象
        mLayoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        initTabs();
        mTabHost.getTabWidget().setShowDividers(0);
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    protected void initData() {

        //自动登入
        AccountManager.getInstance().autoLogon();

        //检测更新
        //默认情况 情况一：wifi模式下提示下载更新
        //开启静默更新情况 情况二：wifi模式下静默下载更新
        if (PrefUtils.getBoolean(GlobalAttribute.IS_SILENT_UPDATE,true)) {
            //静默更新
            BmobUpdateAgent.silentUpdate(this);
        } else {
            //提示更新
            BmobUpdateAgent.update(this);
        }
    }


    /**
     * 初始化底部的4个导航页 推荐
     */
    private void initTabs() {

        MainTabEntity[] tabs = MainTabEntity.values();
        for (MainTabEntity mainTab : tabs) {
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


    }
    

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (PrefUtils.getBoolean(GlobalAttribute.IS_DOUBLE_CLICK_QUIT,true)){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    ToastUtils.showShort("再按一次退出电影天堂");
                    firstTime = System.currentTimeMillis();
                } else {
                    BaseActivity.getForegroundActivity().killAll();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
