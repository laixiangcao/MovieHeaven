package com.jiumeng.movieheaven.entity;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobUser;

/**
 * Created by jiumeng on 2016/10/16.
 */

public class UserEntity extends BmobUser {

    private boolean sex;//性别
    private Bitmap headPortrait;//头像
    private String nickName;//昵称

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
