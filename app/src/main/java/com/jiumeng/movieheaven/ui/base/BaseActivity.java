package com.jiumeng.movieheaven.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiumeng.movieheaven.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Activity基类
 * 1. 重新定义初始化方法 getlayoutId()、initViews()、initData();
 * 2. ButterKnife绑定
 * 3. 统一调度管理，将已启动的Activity添加到集合中，便于程序退出
 * Created by jiumeng on 2016/10/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 记录前台 Activity
     */
    private static BaseActivity sForegroundActivity;
    /**
     * 记录所有没有销毁的 Activity
     */
    private static List<BaseActivity> mActivities = new ArrayList<>();

    /**
     * 获取前台 Activity
     */
    public static BaseActivity getForegroundActivity() {
        return sForegroundActivity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivities.add(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initData();


    /**
     * 启动一个 Activity
     *
     * @param intent
     */
    public static void startAnActivity(Intent intent) {
        if (sForegroundActivity != null) {
            sForegroundActivity.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);
        }
    }


    @Override
    protected void onResume() {
        sForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (sForegroundActivity == this) {
            sForegroundActivity = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mActivities.remove(this);
        super.onDestroy();
    }

    /**
     * 结束所有没有销毁的 Activity, 结束当前进程
     */
    public void killAll() {
        for (BaseActivity activity : mActivities) {
            // 结束当前 Activity, 也可以使用广播
            activity.finish();
        }
        System.exit(0);
    }


}


