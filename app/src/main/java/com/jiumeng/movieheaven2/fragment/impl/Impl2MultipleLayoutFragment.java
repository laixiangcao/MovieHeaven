package com.jiumeng.movieheaven2.fragment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.DataTools;
import com.jiumeng.movieheaven2.provider.MovieListSort;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.provider.ProcessHomeUrl;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cz.msebera.android.httpclient.Header;

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
        //本地缓存加载数据
//        ArrayList<MovieDao> cacheData = (ArrayList<MovieDao>) PrefUtils.readObject("MovieCache" + getMovieType());
//        if (cacheData != null && cacheData.size() > 0) {
//            initData = setMultipeItem(cacheData);
//            loadDataComplete(DataTools.checkData(initData));
//            UIUtils.showToast("从缓存中加载的数据" + cacheData.size());
//            return;
//        }
        //网络加载数据
        loadNetData(isFirstLoad);
    }

    private void loadNetData(final boolean isFirstLoad) {
        NetWorkApi.getMovieDetailInfo(NetWorkApi.HOST, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content =UIUtils.byteArray2String(responseBody);

                if (isFirstLoad) {
                    ArrayList<ArrayList<String>> homeUrls = ProcessHomeUrl.getHomeUrl(content);
                    for (ArrayList<String> homeUrl : homeUrls) {
                        for (String s : homeUrl) {
                            MovieDao movieDao = new MovieDao();
                            movieDao.url=s;
                            getMovieDetail(movieDao);
                        }
                    }

                } else {
                    //刷新时进入此方法 待实现
                    mSwipRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
                loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
            }
        });
    }

    private void getMovieDetail(final MovieDao movieDao) {
        NetWorkApi.getMovieDetailInfo(movieDao.url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content =UIUtils.byteArray2String(responseBody);
                MovieDao movieDetails = ProcessData.parseMovieDetails(content, movieDao,true);
                datalist.add(movieDetails);
                if (datalist.size() == 15) {
                    Collections.sort(datalist, new MovieListSort());
                    initDataList.add(datalist);
                    datalist.clear();
                    initData = setMultipeItem2(initDataList);
                    PrefUtils.saveObject("MovieCache" + getMovieType(), initDataList);
                    loadDataComplete(DataTools.checkData(initDataList));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UIUtils.showToast(movieDao.minName + "----加载失败");
            }
        });
    }


    @Override
    public void onRefresh() {
        mSwipRefresh.setRefreshing(true);
        initPageData(false);
    }

}
