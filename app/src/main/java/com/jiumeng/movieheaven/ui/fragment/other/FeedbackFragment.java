package com.jiumeng.movieheaven.ui.fragment.other;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiumeng on 2016/10/24.
 */
public class FeedbackFragment extends BaseFragment {
    @BindView(R.id.rb_feedback_error)
    RadioButton rbFeedbackError;
    @BindView(R.id.rb_feedback_function)
    RadioButton rbFeedbackFunction;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initUI() {
        rbFeedbackError.toggle();
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_img, R.id.iv_clear_img, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
                break;
            case R.id.iv_clear_img:
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
