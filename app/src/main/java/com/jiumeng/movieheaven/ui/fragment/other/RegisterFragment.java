package com.jiumeng.movieheaven.ui.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.ui.base.BaseActivity;
import com.jiumeng.movieheaven.engine.AccountManager;
import com.jiumeng.movieheaven.entity.UserEntity;
import com.jiumeng.movieheaven.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jiumeng on 2016/10/16.
 */
public class RegisterFragment extends Fragment {
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
                registerUser();
                break;
            case R.id.tv_home:
                break;
        }
    }

    private void registerUser() {
        String userName = etUserName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        AccountManager.getInstance().register(userName, email, password, new SaveListener<UserEntity>() {
            @Override
            public void done(UserEntity user, BmobException e) {
                if (e == null) {
                    ToastUtils.showShort("注册成功");
                    AccountManager.getInstance().autoLogon();
                    BaseActivity.getForegroundActivity().finish();
                } else {
                    ToastUtils.showShort(e.getLocalizedMessage());
                }
            }
        });

    }
}
