package com.jiumeng.movieheaven.ui.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.ui.base.BaseActivity;
import com.jiumeng.movieheaven.ui.activity.BlankActivity;
import com.jiumeng.movieheaven.global.GlobalAttribute;
import com.jiumeng.movieheaven.utils.ImageCatchUtil;
import com.jiumeng.movieheaven.utils.PrefUtils;
import com.jiumeng.movieheaven.ui.views.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class SettingFragment extends Fragment {


    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tb_dbclick_quit)
    ToggleButton tbDbClickQuit;
    @BindView(R.id.tb_is_silent_update)
    ToggleButton tbDetectionUpdate;
    @BindView(R.id.tb_loading_img)
    ToggleButton tbLoadingImg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_settings, null);
        ButterKnife.bind(this, mRootView);
        initUI();
        return mRootView;
    }

    private void initUI() {

        setCacheSize();

        //双击退出、静默更新、加载图片 默认为true
        if (PrefUtils.getBoolean(GlobalAttribute.IS_DOUBLE_CLICK_QUIT, true)) {
            tbDbClickQuit.setToggleOn();
        } else {
            tbDbClickQuit.setToggleOff();
        }
        if (PrefUtils.getBoolean(GlobalAttribute.IS_SILENT_UPDATE, true)) {
            tbDetectionUpdate.setToggleOn();
        } else {
            tbDetectionUpdate.setToggleOff();
        }
        if (PrefUtils.getBoolean(GlobalAttribute.IS_LOAD_PICTURE, true)) {
            tbLoadingImg.setToggleOn();
        } else {
            tbLoadingImg.setToggleOff();
        }

        tbLoadingImg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean(GlobalAttribute.IS_LOAD_PICTURE, on);
            }
        });
        tbDetectionUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean(GlobalAttribute.IS_SILENT_UPDATE, on);
            }
        });
        tbDbClickQuit.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                PrefUtils.putBoolean(GlobalAttribute.IS_DOUBLE_CLICK_QUIT, on);
            }
        });


    }

    private void setCacheSize() {
        String cacheSize = ImageCatchUtil.getInstance().getCacheSize();
        tvCacheSize.setText(cacheSize);
    }

    @OnClick({R.id.rl_notification_settings, R.id.rl_clean_cache, R.id.rl_about, R.id.rl_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_notification_settings:
                BlankActivity activity = (BlankActivity) getActivity();
                activity.setDefFragment(BlankActivity.FRAGMENT_TYPE_SETTING_NOTICE);
                break;
            case R.id.rl_clean_cache:
                ImageCatchUtil.getInstance().clearImageDiskCache(new ImageCatchUtil.ClearCacheListener() {
                    @Override
                    public void update(String cache) {
                        tvCacheSize.setText(cache);
                    }
                });
                break;
            case R.id.rl_about:
                ((BlankActivity) getActivity()).setDefFragment(BlankActivity.FRAGMENT_TYPE_OBOUT);
                break;
            case R.id.rl_exit:
                BaseActivity.getForegroundActivity().killAll();
                break;
        }
    }
}
