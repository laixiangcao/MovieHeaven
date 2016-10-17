package com.jiumeng.movieheaven2.entity;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobUser;

/**
 * Created by jiumeng on 2016/10/16.
 */

public class UserEntity extends BmobUser {
    private String userName;
    private String password;
    private String email;
    private boolean emailVerified;//邮箱认证状态
    private String mobilePhoneNumber;
    private boolean mobilePhoneNumberVerified;//手机号码的认证状态
    private boolean sex;//性别
    private Bitmap headPortrait;//头像
    private String nickName;//昵称

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public boolean isMobilePhoneNumberVerified() {
        return mobilePhoneNumberVerified;
    }

    public void setMobilePhoneNumberVerified(boolean mobilePhoneNumberVerified) {
        this.mobilePhoneNumberVerified = mobilePhoneNumberVerified;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Bitmap getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(Bitmap headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
