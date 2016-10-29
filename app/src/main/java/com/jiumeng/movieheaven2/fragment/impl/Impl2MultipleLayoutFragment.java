package com.jiumeng.movieheaven2.fragment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.network.MyStringCallback;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.DataTools;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by jiumeng on 2016/10/4.
 */

public abstract class Impl2MultipleLayoutFragment extends BaseMultipleLayoutFragment {

    private RecyclerViewBaseAdapter mAdapter;
    private List<MultipleItemEntity> initData;
    private ArrayList<ArrayList<MovieEntity>> data;


    @Override
    protected void initRecyclerView() {
        mAdapter = new RecyclerViewBaseAdapter(initData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);//如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected abstract int getMovieType();

    @Override
    public List<MultipleItemEntity> setMultipeItem(ArrayList<MovieEntity> data) {
        return null;
    }

    public abstract List<MultipleItemEntity> setMultipeItem2(ArrayList<ArrayList<MovieEntity>> data);

    protected void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    @Override
    protected void initPageData(final boolean isFirstLoad) {

        if (isFirstLoad) {
            //读取缓存
            initData = (List<MultipleItemEntity>) PrefUtils.readObject("MovieType:" + getMovieType());
            if (initData != null) {
                loadDataComplete(DataTools.checkData(initData));
                return;
            }
        }

        NetWorkApi.getMovieDetailInfo(NetWorkApi.HOST, this, new MyStringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                UIUtils.showToast(e.getLocalizedMessage());
                if (isFirstLoad){
                    loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
                }else {
                    mSwipRefresh.setRefreshing(false);
                }


            }

            @Override
            public void onResponse(String response, int id) {
                ArrayList<ArrayList<String>> homeUrl = ProcessData.getHomeUrl(response);
                if (homeUrl != null) {
                    data = new ArrayList<>();
                    for (ArrayList<String> urlList : homeUrl) {
                        setSubRecommend(urlList,isFirstLoad);
                    }
                }


            }
        });
    }

    private void setSubRecommend(final ArrayList<String> urlList, final boolean isFirstLoad) {
        final ArrayList<MovieEntity> movieList = new ArrayList<>();
        for (final String url : urlList) {
            NetWorkApi.getMovieDetailInfo(url, this, new MyStringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    UIUtils.showToast(e.getLocalizedMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    MovieEntity movie = new MovieEntity();
                    movie.url = url;
                    movie = ProcessData.parseMovieDetails(response, movie, true);
                    movieList.add(movie);
                    if (movieList.size() == urlList.size()) {
                        data.add(movieList);
                        if (data.size() == 4) {
                            initData = setMultipeItem2(data);
                            PrefUtils.saveObject("MovieType:" + getMovieType(), initData);
                            if (isFirstLoad){
                                loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
                            }else {
                                mAdapter.setNewData(initData);
                                mSwipRefresh.setRefreshing(false);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        mSwipRefresh.setRefreshing(true);
        initPageData(false);
    }

}
