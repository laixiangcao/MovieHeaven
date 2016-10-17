package com.jiumeng.movieheaven2.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BlankActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiumeng on 2016/10/16.
 */
public class registerFragment extends Fragment {
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_register, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.btn_register, R.id.tv_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            case R.id.tv_home:
                break;
        }
    }
}
