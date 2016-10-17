package com.jiumeng.movieheaven2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.fragment.other.MovieDetailsFragment;
import com.jiumeng.movieheaven2.fragment.other.NoticeSettingFragment;
import com.jiumeng.movieheaven2.fragment.other.SearchFragment;
import com.jiumeng.movieheaven2.fragment.other.SettingFragment;
import com.jiumeng.movieheaven2.fragment.other.loginFragment;
import com.jiumeng.movieheaven2.fragment.other.registerFragment;

/**
 * Created by jiumeng on 2016/10/15.
 */

public class BlankActivity extends BaseActivity {
    public static final int FRAGMENT_TYPE_MOVIEDETAIL = 0;
    public static final int FRAGMENT_TYPE_SETTING = 1;
    public static final int FRAGMENT_TYPE_SETTING_NOTICE = 2;
    public static final int FRAGMENT_TYPE_SEARCH = 3;
    public static final int FRAGMENT_TYPE_LOGIN = 4;
    public static final int FRAGMENT_TYPE_REGISTER = 5;
    private Bundle bundle;


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_blank);
    }

    @Override
    protected void loadData() {
        bundle = getIntent().getExtras();
        setDefFragment(bundle.getInt("fragmentType"));
    }

    public void setDefFragment(int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        switch (type) {
            case FRAGMENT_TYPE_MOVIEDETAIL:
                MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                bundle.remove("fragmentType");
                movieDetailsFragment.setArguments(bundle);
                ft.replace(R.id.fl_content, movieDetailsFragment);
                break;
            case FRAGMENT_TYPE_SETTING:
                ft.replace(R.id.fl_content, new SettingFragment());
                break;
            case FRAGMENT_TYPE_SETTING_NOTICE:
                ft.replace(R.id.fl_content, new NoticeSettingFragment());
                ft.addToBackStack(null);
                break;
            case FRAGMENT_TYPE_SEARCH:
                ft.replace(R.id.fl_content, new SearchFragment());
                break;
            case FRAGMENT_TYPE_LOGIN:
                ft.replace(R.id.fl_content, new loginFragment());
                break;
            case FRAGMENT_TYPE_REGISTER:
                ft.replace(R.id.fl_content, new registerFragment());
                ft.addToBackStack(null);
                break;
        }
        ft.commit();
    }


}
