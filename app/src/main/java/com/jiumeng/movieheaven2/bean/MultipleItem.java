package com.jiumeng.movieheaven2.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;


/**
 * Created by jiumeng on 2016/9/27.
 */
public class MultipleItem implements MultiItemEntity {
    public static final int LIST = 0;
    public static final int GRID = 1;
    public static final int RECOMMEND = 2;
    public static final int AD = 3;
    private  ArrayList<MovieDao> dataList;
    private MovieDao data;
    private int spanSize;   //距距列数
    private int itemType;

    public MultipleItem(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public MultipleItem(int itemType, int spanSize, MovieDao data) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.data = data;
    }
    public MultipleItem(int itemType, int spanSize, ArrayList<MovieDao> dataList) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.dataList = dataList;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public MovieDao getData() {
        return data;
    }
    public ArrayList<MovieDao> getDataList() {
        return dataList;
    }

    public void setData(MovieDao data){
        this.data=data;
    }
}
