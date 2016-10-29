package com.jiumeng.movieheaven2.fragment.impl;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BlankActivity;
import com.jiumeng.movieheaven2.activity.MovieDetailsActivity;
import com.jiumeng.movieheaven2.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseMultipleLayoutFragment;
import com.jiumeng.movieheaven2.network.MyStringCallback;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.DataTools;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.R.attr.data;


/**
 * Created by jiumeng on 2016/10/4.
 */

public abstract class ImplMultipleLayoutFragment extends BaseMultipleLayoutFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerViewBaseAdapter mAdapter;
    private int mCurrentPage = 1;
    private View notLoadingView;
    private int mPageCount = 2;
    private List<MultipleItemEntity> initData;
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
                List<MultipleItemEntity> data = mAdapter.getData();
                return data.get(i).getSpanSize();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultipleItemEntity item = (MultipleItemEntity) baseQuickAdapter.getData().get(i);
//                Intent intent = new Intent(getContext(), BlankActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("movie", item.getData());
//                bundle.putInt("fragmentType", BlankActivity.FRAGMENT_TYPE_MOVIE_DETAIL);
//                intent.putExtras(bundle);
//                getContext().startActivity(intent);

                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, item.getData());
                intent.putExtra(MovieDetailsActivity.EXTRA_NEED_LOAD_DETAIL,true);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "");
                    startActivity(intent, options.toBundle());
                }else {
                    startActivity(intent);
                }


            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected abstract int getMovieType();

    @Override
    public abstract List<MultipleItemEntity> setMultipeItem(ArrayList<MovieEntity> data);

    protected void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    @Override
    protected void initPageData(final boolean isFirstLoad) {
        if (isFirstLoad) {
            //读取缓存
            initData = (List<MultipleItemEntity>) PrefUtils.readObject("MovieType:" + getMovieType());
//            mPageCount = PrefUtils.getInt("MovieTypePages:" + getMovieType());
            if (initData != null) {
                loadDataComplete(DataTools.checkData(initData));
                return;
            }
        }
        requestNetData(isFirstLoad);

    }

    private void requestNetData(final boolean isFirstLoad) {
        NetWorkApi.getPageInfoFromNet(getMovieType(), 1, this, new MyStringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isFirstLoad) {
                    loadDataComplete(LoadingPage.ResultState.STATE_NONETWORK);
                }
                Toast.makeText(getContext(), "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                mPageCount = ProcessData.getPages(response);
//                PrefUtils.putInt("MovieTypePages:" + getMovieType(),mPageCount);
                System.out.println("aaa:当前类型" + getMovieType() + "总数----" + mPageCount);
                ArrayList<MovieEntity> data = ProcessData.parsePageData(response);
                if (isFirstLoad) {
                    initData = setMultipeItem(data);
                    PrefUtils.saveObject("MovieType:" + getMovieType(), initData);
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
                public void onError(Call call, Exception e, int id) {
                    mCurrentPage--;
                    mAdapter.showLoadMoreFailedView();
                }

                @Override
                public void onResponse(String response, int id) {
                    ArrayList<MovieEntity> datalist = ProcessData.parsePageData(response);
                    if (mPageCount == 2) {
                        mPageCount = ProcessData.getPages(response);
                    }
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
                MultipleItemEntity multipleItem = (MultipleItemEntity) mAdapter.getData().get(0);
                firstID = multipleItem.getData().id;
                initPageData(false);
            }
        }, 1000);
    }

    private void setRefreshData(ArrayList<MovieEntity> data) {
        //刷新时重新获取第一页的数据，将第一页数据的第一个Movie的id
        // 与当前列表展示的第一个Movie的id 对比
        ArrayList<MovieEntity> newData = new ArrayList<>();
        for (MovieEntity movieDao : data) {
            if (movieDao.id.equals(firstID)) {
                break;
            } else {
                newData.add(movieDao);
            }
        }
        if (newData.size() != 0) {
            List<MultipleItemEntity> newDataList = setMultipeItem(newData);
            mAdapter.getData().addAll(0, newDataList);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "没有新数据", Toast.LENGTH_SHORT).show();
        }
        mSwipRefresh.setRefreshing(false);
    }

}
