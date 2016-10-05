package com.jiumeng.movieheaven2.fragment.impl;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.global.GlobalSetting;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.DataTools;
import com.jiumeng.movieheaven2.provider.MovieListSort;
import com.jiumeng.movieheaven2.provider.ProcessData;
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

public abstract class ImplMultipleLayoutFragment extends BaseMultipleLayoutFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerViewBaseAdapter mAdapter;
    private int mCurrentPage = 1;
    private View notLoadingView;
    private int mPageCount;
    private List<MultipleItem> initData;
    private String firstID;
    private ArrayList<MovieDao> initDatalist;
    private ArrayList<MovieDao> refreshDatalist;

    @Override
    protected void initRecyclerView() {
        mAdapter = new RecyclerViewBaseAdapter(initData);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(30);
//        mAdapter.setLoadingView();//设置加载更多时的布局
//        mAdapter.setLoadMoreFailedView();//设置加载更多时失败的布局
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setHasFixedSize(true);//如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                List<MultipleItem> data = mAdapter.getData();
                return data.get(i).getSpanSize();
            }
        });
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

    protected void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    @Override
    protected void initPageData(final boolean isFirstLoad) {
        if (isFirstLoad) {
//            ArrayList<MovieDao> cacheData = (ArrayList<MovieDao>) PrefUtils.readObject("MovieCache" + getMovieType());
//            if (cacheData != null && cacheData.size() > 0) {
//                initData = setMultipeItem(cacheData);
//                loadDataComplete(DataTools.checkData(initData));
//                UIUtils.showToast("从缓存中加载的数据" + cacheData.size());
//                return;
//            }
        }

        NetWorkApi.getPageInfoFromNet(getMovieType(), 1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content =UIUtils.byteArray2String(responseBody);
                mPageCount = ProcessData.getPages(content);
                System.out.println("test:" + getMovieType() + "----" + mPageCount);
                ArrayList<MovieDao> data = ProcessData.parsePageData(content);

                if (isFirstLoad) {
                    if (GlobalSetting.isNoPicModel()) {
                        if (data.size() > 0 ) {
                            PrefUtils.saveObject("MovieCache" + getMovieType(), data);
                        }
                        initData = setMultipeItem(data);
                        loadDataComplete(DataTools.checkData(initData));
                    } else {
                        initDatalist = new ArrayList<>();
                        for (MovieDao movieDao : data) {
                            getMovieDetail(false, movieDao, data.size());
                        }
                    }

                } else {
                    //刷新时重新获取第一页的数据，将第一页数据的第一个Movie的id
                    // 与当前列表展示的第一个Movie的id 对比
                    ArrayList<MovieDao> newData = new ArrayList<>();
                    for (MovieDao movieDao : data) {
                        if (movieDao.id.equals(firstID)) {
                            break;
                        } else {
                            newData.add(movieDao);
                        }
                    }
                    if (newData.size() != 0) {
                        List<MultipleItem> newDataList = setMultipeItem(newData);
                        mAdapter.getData().addAll(0, newDataList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "没有新数据", Toast.LENGTH_SHORT).show();
                    }
                    mSwipRefresh.setRefreshing(false);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
                if (isFirstLoad) {
                    loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
                } else {
                    Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getMovieDetail(final boolean isLoadMore, final MovieDao movieDao, final int size) {
        NetWorkApi.getMovieDetailInfo(movieDao.url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content =UIUtils.byteArray2String(responseBody);
                MovieDao movieDetails = ProcessData.parseMovieDetails(content, movieDao,false);
                if (isLoadMore) {
                    refreshDatalist.add(movieDetails);
                    if (refreshDatalist.size() == size) {
                        Collections.sort(refreshDatalist, new MovieListSort());
                        List<MultipleItem> moreData = setMultipeItem(refreshDatalist);
                        mAdapter.addData(moreData);
                    }
                } else {
                    initDatalist.add(movieDetails);
                    if (initDatalist.size() == size) {
                        Collections.sort(initDatalist, new MovieListSort());
                        initData = setMultipeItem(initDatalist);
                        loadDataComplete(DataTools.checkData(initData));
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UIUtils.showToast(movieDao.minName + "----加载失败");
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        System.out.println("aaa:当前页数----" + (++mCurrentPage));
        if (mCurrentPage >= mPageCount) {

            if (notLoadingView == null) {
                notLoadingView = mLayoutInflater.inflate(R.layout.not_loading, null);
            }
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(notLoadingView);
                }
            }, 1000);

        } else {
            NetWorkApi.getPageInfoFromNet(getMovieType(), ++mCurrentPage, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String content =UIUtils.byteArray2String(responseBody);
                    ArrayList<MovieDao> datalist = ProcessData.parsePageData(content);
                    if (datalist == null) {
                        //当加载更多数据返回数据为null时，显示加载失败的布局
                        //点击重新加载 会默认再次调用 加载更多的方法，所以这里需要将当前的页数减一
                        mCurrentPage--;
                        mAdapter.showLoadMoreFailedView();
                    } else {
                        if (datalist.size() == 0) {
                            mAdapter.addFooterView(notLoadingView);
                        } else {

                            refreshDatalist = new ArrayList<>();
                            for (MovieDao movieDao : datalist) {
                                getMovieDetail(true, movieDao, datalist.size());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }


    @Override
    public void onRefresh() {
        mSwipRefresh.setRefreshing(true);
        mSwipRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaa:当前刷新的界面是" + getMovieType());
                MultipleItem multipleItem = (MultipleItem) mAdapter.getData().get(0);
                firstID = multipleItem.getData().id;
                initPageData(false);
            }
        }, 1000);
    }

}
