package com.jiumeng.movieheaven2.activity;

import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.engine.AccountManager;
import com.jiumeng.movieheaven2.entity.MainTabEntity;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;

import butterknife.BindView;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;


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
        AccountManager.getInstance().autoLogon();

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
        System.out.println("update:"+PrefUtils.getBoolean("isDblclickQuit",true));
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                if (updateStatus == UpdateStatus.Yes) {//版本有更新

                }else if(updateStatus == UpdateStatus.No){
                    Toast.makeText(MainActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                    Toast.makeText(MainActivity.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.IGNORED){
                    Toast.makeText(MainActivity.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.ErrorSizeFormat){
                    Toast.makeText(MainActivity.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.TimeOut){
                    Toast.makeText(MainActivity.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //检测更新
        //默认情况 情况一：wifi模式下提示下载更新
        //开启静默更新情况 情况二：wifi模式下静默下载更新
        if (PrefUtils.getBoolean("isDetectionUpdate",true)) {
            //静默更新
            BmobUpdateAgent.silentUpdate(this);
        } else {
            //提示更新
            BmobUpdateAgent.update(this);
        }
    }


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

        if (PrefUtils.getBoolean("isDblclickQuit",true)){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    UIUtils.showToast("再按一次退出电影天堂");
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
