package com.jiumeng.movieheaven2.fragment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiumeng on 2016/10/4.
 */

public abstract class Impl2MultipleLayoutFragment extends BaseMultipleLayoutFragment {

    private RecyclerViewBaseAdapter mAdapter;
    private List<MultipleItemEntity> initData;
    private ArrayList<MovieEntity> datalist=new ArrayList<>();
    private ArrayList<ArrayList<MovieEntity>> initDataList;


    @Override
    protected void initRecyclerView() {
        mAdapter = new RecyclerViewBaseAdapter(initData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);//如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultipleItemEntity item = (MultipleItemEntity) baseQuickAdapter.getData().get(i);
                UIUtils.showToast(item.getData().minName);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected abstract int getMovieType();

    @Override
    public abstract List<MultipleItemEntity> setMultipeItem(ArrayList<MovieEntity> data);

    public abstract List<MultipleItemEntity> setMultipeItem2(ArrayList<ArrayList<MovieEntity>> data);

    protected void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    @Override
    protected void initPageData(final boolean isFirstLoad) {
        initDataList = new ArrayList<>();

    }




    @Override
    public void onRefresh() {
        mSwipRefresh.setRefreshing(true);
        initPageData(false);
    }

}
