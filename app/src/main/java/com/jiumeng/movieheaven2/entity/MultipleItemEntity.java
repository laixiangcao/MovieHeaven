package com.jiumeng.movieheaven2.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by jiumeng on 2016/9/27.
 */
public class MultipleItemEntity implements MultiItemEntity, Serializable {
    //    private static final long SerializVersion=1L;
    public static final int LIST = 0;
    public static final int GRID = 1;
    public static final int RECOMMEND = 2;
    public static final int AD = 3;
    private ArrayList<MovieEntity> dataList;
    private MovieEntity data;
    private int spanSize;   //距距列数
    private int itemType;

    public MultipleItemEntity(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public MultipleItemEntity(int itemType, int spanSize, MovieEntity data) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.data = data;
    }

    public MultipleItemEntity(int itemType, int spanSize, ArrayList<MovieEntity> dataList) {
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

    public MovieEntity getData() {
        return data;
    }

    public ArrayList<MovieEntity> getDataList() {
        return dataList;
    }

    public void setData(MovieEntity data) {
        this.data = data;
    }
}
