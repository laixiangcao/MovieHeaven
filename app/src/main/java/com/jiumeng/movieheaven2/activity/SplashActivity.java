package com.jiumeng.movieheaven2.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.jiumeng.movieheaven2.R;

import butterknife.BindView;

/**
 * Created by jiumeng on 2016/10/30.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.5f);
//        translateAnimation.setDuration(1000);
//        ivLogo.startAnimation(translateAnimation);
        ivLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        },2000);
    }

    @Override
    protected void initData() {

    }
}
