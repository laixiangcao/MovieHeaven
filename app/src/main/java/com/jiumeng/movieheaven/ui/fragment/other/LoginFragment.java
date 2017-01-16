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
import com.jiumeng.movieheaven.ui.activity.BlankActivity;
import com.jiumeng.movieheaven.engine.AccountManager;
import com.jiumeng.movieheaven.entity.UserEntity;
import com.jiumeng.movieheaven.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by jiumeng on 2016/10/16.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_login, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginUser();
                break;
            case R.id.tv_register:
                BlankActivity activity = (BlankActivity) getActivity();
                activity.setDefFragment(BlankActivity.FRAGMENT_TYPE_REGISTER);
                break;
            case R.id.tv_home:
                break;
        }
    }

    private void loginUser() {
        AccountManager.getInstance().login(etUserName.getText().toString(), etPassword.getText().toString(), new LogInListener<UserEntity>() {
            @Override
            public void done(UserEntity user, BmobException e) {
                if (user != null) {
                    ToastUtils.showShort("登入成功");
                    AccountManager.getInstance().setLoginStatus(true);
                    BaseActivity.getForegroundActivity().finish();
                }else {
                    ToastUtils.showShort(e.getLocalizedMessage());
                }
            }
        });
    }
}
