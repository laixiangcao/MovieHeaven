package com.jiumeng.movieheaven2.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BaseActivity;
import com.jiumeng.movieheaven2.engine.UserManager;
import com.jiumeng.movieheaven2.entity.UserEntity;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiumeng on 2016/10/17.
 */

public class UserInfoFragment extends Fragment {
    private static final int DIALOG_TYPE_ALTER_AVATAR = 0;
    private static final int DIALOG_TYPE_ALTER_NICK = 1;
    private static final int DIALOG_TYPE_BOUND_EMAIL = 2;
    private static final int DIALOG_TYPE_BOUND_PHONE = 3;
    private static final int DIALOG_TYPE_ALTER_PWD = 4;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_user_info, null);
        ButterKnife.bind(this, rootView);
        showUserInfo();
        return rootView;
    }

    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        UserEntity user = UserManager.getInstance().getCurrentUser();
        if (!MyTextUtils.isEmpty(user.getEmail())) {
            tvEmail.setText(user.getEmail());
        }
        if (!MyTextUtils.isEmpty(user.getMobilePhoneNumber())) {
            tvPhone.setText(user.getMobilePhoneNumber());
        }
        if (!MyTextUtils.isEmpty(user.getNickName())) {
            tvNick.setText(user.getNickName());
        }
    }

    @OnClick({R.id.alter_avatar, R.id.alter_nick, R.id.bound_email, R.id.bound_phone, R.id.alter_pwd, R.id.logout_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alter_avatar:
                break;
            case R.id.alter_nick:
                break;
            case R.id.bound_email:
                boundEmail();
                break;
            case R.id.bound_phone:
                break;
            case R.id.alter_pwd:
                break;
            case R.id.logout_user:
                UserManager.getInstance().logout();
                BaseActivity.getForegroundActivity().finish();
                break;
        }
    }

    private void boundEmail() {
        UserManager userManager = UserManager.getInstance();
        final String email = userManager.getCurrentUser().getEmail();
        userManager.emailVerify(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    UIUtils.showToast("请求验证邮件成功，请到" + email + "邮箱中进行激活。");
                }else{
                    UIUtils.showToast("请求失败:" + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 根据类型显示对话框
     *
     * @param type
     */
    private void showDialog(int type) {
        switch (type) {
            case DIALOG_TYPE_ALTER_AVATAR:
                break;
            case DIALOG_TYPE_ALTER_NICK:
                break;
            case DIALOG_TYPE_BOUND_EMAIL:
                break;
            case DIALOG_TYPE_BOUND_PHONE:
                break;
            case DIALOG_TYPE_ALTER_PWD:
                break;

        }
    }
}
