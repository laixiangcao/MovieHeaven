package com.jiumeng.movieheaven2.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BlankActivity;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.ToggleButton;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    private RelativeLayout mNotify, mClearCache, mAbout, mQuit;
    private ToggleButton mDblclickQuit, mDetectionUpdate, mLoadPicture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_settings, null);
        initUI();
        return mRootView;
    }

    private void initUI() {
        mNotify = (RelativeLayout) mRootView.findViewById(R.id.rl_notification_settings);
        mClearCache = (RelativeLayout) mRootView.findViewById(R.id.rl_clean_cache);
        mAbout = (RelativeLayout) mRootView.findViewById(R.id.rl_about);
        mQuit = (RelativeLayout) mRootView.findViewById(R.id.rl_exit);
        mNotify.setOnClickListener(this);
        mClearCache.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mQuit.setOnClickListener(this);

        mDblclickQuit = (ToggleButton) mRootView.findViewById(R.id.tb_dblclick_quit);
        mDetectionUpdate = (ToggleButton) mRootView.findViewById(R.id.tb_detection_update);
        mLoadPicture = (ToggleButton) mRootView.findViewById(R.id.tb_loading_img);

        if (PrefUtils.getBoolean("isDblclickQuit")){
            mDblclickQuit.setToggleOn();
        }else {
            mDblclickQuit.setToggleOff();
        }
        if (PrefUtils.getBoolean("isDetectionUpdate")){
            mDetectionUpdate.setToggleOn();
        }else {
            mDetectionUpdate.setToggleOff();
        }
        if (PrefUtils.getBoolean("isLoadPicture")){
            mLoadPicture.setToggleOn();
        }else {
            mLoadPicture.setToggleOff();
        }

        mLoadPicture.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean("isLoadPicture",on);
            }
        });
        mDetectionUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean("isDetectionUpdate",on);
            }
        });
        mDblclickQuit.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean("isDblclickQuit",on);
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_notification_settings:
                BlankActivity activity = (BlankActivity) getActivity();
                activity.setDefFragment(BlankActivity.FRAGMENT_TYPE_SETTING_NOTICE);
                break;
            case R.id.rl_clean_cache:
                UIUtils.showToast("清理缓存");
                break;
            case R.id.rl_about:
                UIUtils.showToast("关于");
                break;
            case R.id.rl_exit:
                UIUtils.showToast("退出");
                break;
        }
    }
}
