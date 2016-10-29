package com.jiumeng.movieheaven2.engine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jiumeng.movieheaven2.activity.BaseActivity;
import com.jiumeng.movieheaven2.activity.BlankActivity;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MyFavoriteEntity;
import com.jiumeng.movieheaven2.entity.UserEntity;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by jiumeng on 2016/10/16.
 */

public class AccountManager {
    private static AccountManager userManager;
    private boolean isLogin;

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (userManager == null) {
            synchronized (AccountManager.class) {
                if (userManager == null) {
                    userManager = new AccountManager();
                }
            }
        }
        return userManager;
    }

    public void login(String account, String pwd, LogInListener<UserEntity> logInListener) {
        if (MyTextUtils.isEmpty(account) || MyTextUtils.isEmpty(pwd)) {
            UIUtils.showToast("账号或密码为空");
            return;
        }
        BmobUser.loginByAccount(account, pwd, logInListener);
    }

    /**
     * 注册用户
     * @param userName 用户名
     * @param email 邮箱
     * @param password 密码
     * @param saveListener
     */
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

    public void addFavorite(Context context,MovieEntity favorite, SaveListener<String> saveListener) {
        if (isLogin){
            MyFavoriteEntity favoriteEntity = new MyFavoriteEntity();
            favoriteEntity.setAuthor(getCurrentUser().getUsername());
            favoriteEntity.setMovieName(favorite.name);
            favoriteEntity.setMovieUrl(favorite.url);
            favoriteEntity.save(saveListener);
        }else {
            Intent intent = new Intent(context, BlankActivity.class);
            intent.putExtra(BlankActivity.FRAGMENT_TYPE, BlankActivity.FRAGMENT_TYPE_LOGIN);
            context.startActivity(intent);
        }

    }
    public void seeMyFavorite(Context context,FindListener<MyFavoriteEntity> Listener) {
        if (isLogin){
            BmobQuery<MyFavoriteEntity> query = new BmobQuery<>();
            query.addWhereEqualTo("author", getCurrentUser().getUsername());
            //默认返回10条数据
            //执行查询方法
            query.findObjects(Listener);

        }else {
            Intent intent = new Intent(context, BlankActivity.class);
            intent.putExtra(BlankActivity.FRAGMENT_TYPE, BlankActivity.FRAGMENT_TYPE_LOGIN);
            context.startActivity(intent);
        }

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
