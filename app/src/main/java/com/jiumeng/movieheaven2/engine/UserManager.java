package com.jiumeng.movieheaven2.engine;

import com.jiumeng.movieheaven2.entity.UserEntity;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by jiumeng on 2016/10/16.
 */

public class UserManager {
    private static UserManager userManager;
    private boolean isLogin;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public void register(String userName, String email, String password, SaveListener<UserEntity> saveListener) {
        if (MyTextUtils.isEmpty(userName) || MyTextUtils.isEmpty(password) || MyTextUtils.isEmpty(email)) {
            UIUtils.showToast("请正确填写信息");
            return;
        }
        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(saveListener);
    }

    public void login(String account, String pwd, LogInListener<UserEntity> logInListener) {
        if (MyTextUtils.isEmpty(account) || MyTextUtils.isEmpty(pwd)) {
            UIUtils.showToast("账号或密码为空");
            return;
        }
        BmobUser.loginByAccount(account, pwd, logInListener);

    }

    public void emailVerify(String email, UpdateListener listener) {
        BmobUser.requestEmailVerify(email, listener);
    }

    public void resetPassWordByEmail(String email, UpdateListener updateListener) {
        BmobUser.resetPasswordByEmail(email, updateListener);
    }

    public void setLoginStatus(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getLoginStatus() {
        return this.isLogin;
    }

    public void autoLogon() {
        UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
        if (currentUser != null) {
            // 允许用户使用应用
            UIUtils.showToast("自动登入成功");
            isLogin = true;
        }
    }

    public void logout() {
        BmobUser.logOut();   //清除缓存用户对象
        isLogin = false;
        UIUtils.showToast("已注销当前用户");
    }

    public UserEntity getCurrentUser() {
        UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
        if (currentUser != null) {
            return currentUser;
        } else {
            UIUtils.showToast("未登录账号");
            return null;
        }
    }
}
