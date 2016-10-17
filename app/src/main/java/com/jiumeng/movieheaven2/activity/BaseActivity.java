package com.jiumeng.movieheaven2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jiumeng on 2016/10/15.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        loadData();
    }

    protected abstract void initViews();

    protected abstract void loadData();
}
