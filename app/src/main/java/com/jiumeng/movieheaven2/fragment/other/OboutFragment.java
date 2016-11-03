package com.jiumeng.movieheaven2.fragment.other;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.fragment.base.BaseFragment;
import com.jiumeng.movieheaven2.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class OboutFragment extends BaseFragment {


    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_check_update)
    RelativeLayout rlCheckUpdate;
    @BindView(R.id.rl_grade)
    RelativeLayout rlGrade;
    @BindView(R.id.tv_oscsite)
    TextView tvOscsite;
    @BindView(R.id.tv_knowmore)
    TextView tvKnowmore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initUI() {
        tvVersionName.setText(UIUtils.getVersionName());
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.rl_check_update, R.id.rl_grade, R.id.tv_oscsite, R.id.tv_knowmore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_check_update:
                //手动检测更新
                BmobUpdateAgent.forceUpdate(getContext());
                break;
            case R.id.rl_grade:
                break;
            case R.id.tv_oscsite:
                break;
            case R.id.tv_knowmore:
                break;
        }
    }
}
