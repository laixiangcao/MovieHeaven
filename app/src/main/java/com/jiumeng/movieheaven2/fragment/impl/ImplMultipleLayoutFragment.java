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
import com.jiumeng.movieheaven2.network.MyStringCallback;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.DataTools;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.utils.UIUtils;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    protected void initRecyclerView() {
        mAdapter = new RecyclerViewBaseAdapter(initData);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(25);
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

        }
        requestNetData(isFirstLoad);

    }

    private void requestNetData(final boolean isFirstLoad) {
        NetWorkApi.getPageInfoFromNet(getMovieType(), 1, this, new MyStringCallback() {
            @Override
            public void onResponse(String response, int id) {
                mPageCount = ProcessData.getPages(response);
                ArrayList<MovieDao> data = ProcessData.parsePageData(response);
                if (isFirstLoad) {
                    initData = setMultipeItem(data);
                    loadDataComplete(DataTools.checkData(initData));
                } else {
                    setRefreshData(data);
                }
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        System.out.println("aaa:当前页数----" + (mCurrentPage));
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
            NetWorkApi.getPageInfoFromNet(getMovieType(), ++mCurrentPage, this, new MyStringCallback() {
                @Override
                public void onResponse(String response, int id) {
                    ArrayList<MovieDao> datalist = ProcessData.parsePageData(response);
                    if (datalist == null) {
                        //当加载更多数据返回数据为null时，显示加载失败的布局
                        //点击重新加载 会默认再次调用 加载更多的方法，所以这里需要将当前的页数减一
                        mCurrentPage--;
                        mAdapter.showLoadMoreFailedView();
                    } else {
                        if (datalist.size() == 0) {
                            mAdapter.addFooterView(notLoadingView);
                        } else {
                            mAdapter.addData(setMultipeItem(datalist));
                        }
                    }
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
                MultipleItem multipleItem = (MultipleItem) mAdapter.getData().get(0);
                firstID = multipleItem.getData().id;
                initPageData(false);
            }
        }, 1000);
    }

    private void setRefreshData(ArrayList<MovieDao> data) {
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
