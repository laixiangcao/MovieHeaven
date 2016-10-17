package com.jiumeng.movieheaven2.engine;

import com.jiumeng.movieheaven2.entity.UserEntity;
import com.jiumeng.movieheaven2.utils.UIUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jiumeng on 2016/10/16.
 */

public class UserManager {

    public void register(String userName, String password, String email) {
        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(new SaveListener<UserEntity>() {
            @Override
            public void done(UserEntity s, BmobException e) {
                if (e == null) {
                    UIUtils.showToast("注册成功");
                } else {
                    UIUtils.showToast(e.toString());
                }
            }
        });
    }
}
