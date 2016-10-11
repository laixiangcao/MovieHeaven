package com.jiumeng.movieheaven2.fragment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiumeng on 2016/10/4.
 */

public abstract class Impl2MultipleLayoutFragment extends BaseMultipleLayoutFragment {

    private RecyclerViewBaseAdapter mAdapter;
    private List<MultipleItem> initData;
    private ArrayList<MovieDao> datalist=new ArrayList<>();
    private ArrayList<ArrayList<MovieDao>> initDataList;


    @Override
    protected void initRecyclerView() {
        mAdapter = new RecyclerViewBaseAdapter(initData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);//如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultipleItem item = (MultipleItem) baseQuickAdapter.getData().get(i);
                UIUtils.showToast(item.getData().minName);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected abstract int getMovieType();

    @Override
    public abstract List<MultipleItem> setMultipeItem(ArrayList<MovieDao> data);

    public abstract List<MultipleItem> setMultipeItem2(ArrayList<ArrayList<MovieDao>> data);

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
