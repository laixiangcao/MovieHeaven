package com.jiumeng.movieheaven2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.fragment.other.FeedbackFragment;
import com.jiumeng.movieheaven2.fragment.other.LoginFragment;
import com.jiumeng.movieheaven2.fragment.other.MyFavoriteFragment;
import com.jiumeng.movieheaven2.fragment.other.NoticeSettingFragment;
import com.jiumeng.movieheaven2.fragment.other.OboutFragment;
import com.jiumeng.movieheaven2.fragment.other.RegisterFragment;
import com.jiumeng.movieheaven2.fragment.other.SearchFragment;
import com.jiumeng.movieheaven2.fragment.other.SettingFragment;
import com.jiumeng.movieheaven2.fragment.other.UserInfoFragment;

import butterknife.BindView;

/**
 * Created by jiumeng on 2016/10/15.
 */

public class BlankActivity extends BaseActivity {
    public static final String FRAGMENT_TYPE="fragmentType";
    public static final int FRAGMENT_TYPE_SETTING = 1;
    public static final int FRAGMENT_TYPE_SETTING_NOTICE = 2;
    public static final int FRAGMENT_TYPE_SEARCH = 3;
    public static final int FRAGMENT_TYPE_LOGIN = 4;
    public static final int FRAGMENT_TYPE_REGISTER = 5;
    public static final int FRAGMENT_TYPE_USER_INFO = 6;
    public static final int FRAGMENT_TYPE_OBOUT = 7;
    public static final int FRAGMENT_TYPE_FEEDBACK = 8;
    public static final int FRAGMENT_TYPE_FAVORITE = 9;

    private String title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ActionBar actionBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_blank;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        setDefFragment(bundle.getInt("fragmentType"));

    }

    public void setDefFragment(int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        switch (type) {
            case FRAGMENT_TYPE_SETTING:
                ft.replace(R.id.fl_content, new SettingFragment());
                title="设置中心";
                break;
            case FRAGMENT_TYPE_SETTING_NOTICE:
                ft.replace(R.id.fl_content, new NoticeSettingFragment());
                title="设置中心";
                ft.addToBackStack(null);
                break;
            case FRAGMENT_TYPE_SEARCH:
                ft.replace(R.id.fl_content, new SearchFragment());
                title="搜索";
                break;
            case FRAGMENT_TYPE_LOGIN:
                ft.replace(R.id.fl_content, new LoginFragment());
                title="登入";
                break;
            case FRAGMENT_TYPE_REGISTER:
                ft.replace(R.id.fl_content, new RegisterFragment());
                title="注册";
                ft.addToBackStack(null);
                break;
            case FRAGMENT_TYPE_USER_INFO:
                ft.replace(R.id.fl_content, new UserInfoFragment());
                title="个人中心";
                break;
            case FRAGMENT_TYPE_OBOUT:
                ft.replace(R.id.fl_content, new OboutFragment());
                title="关于电影天堂";
                ft.addToBackStack(null);
                break;
            case FRAGMENT_TYPE_FEEDBACK:
                ft.replace(R.id.fl_content, new FeedbackFragment());
                title="意见反馈";
                break;
            case FRAGMENT_TYPE_FAVORITE:
                ft.replace(R.id.fl_content, new MyFavoriteFragment());
                title="我的收藏";
                break;
        }
        actionBar.setTitle(title);
        ft.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
