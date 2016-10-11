package com.jiumeng.movieheaven2.entity;

import android.os.Bundle;

import java.io.Serializable;


public class ViewPageInfoEntity implements Serializable {


    public String title;
    public  String tag;
    public  Class<?> clss;
    public Bundle mBundle;

    public ViewPageInfoEntity(String _title, String _tag, Class<?> _class, Bundle bundle) {
        title = _title;
        tag = _tag;
        clss = _class;
        mBundle=bundle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
