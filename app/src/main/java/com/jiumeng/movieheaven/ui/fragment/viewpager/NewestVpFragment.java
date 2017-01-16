package com.jiumeng.movieheaven.ui.fragment.viewpager;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.entity.MovieEntity;
import com.jiumeng.movieheaven.http.ApiService;
import com.jiumeng.movieheaven.movietask.TaskDetailContract;
import com.jiumeng.movieheaven.movietask.base.BaseMvpFragment;
import com.jiumeng.movieheaven.movietask.interfaces.OnLoadCompleteListener;
import com.jiumeng.movieheaven.movietask.presenter.MoviePresenter;
import com.jiumeng.movieheaven.ui.adapter.RecyclerViewBaseAdapter;
import com.jiumeng.movieheaven.entity.MultipleItemEntity;
import com.jiumeng.movieheaven.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 推荐页面的viewpager的 最新电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class NewestVpFragment extends BaseMvpFragment<MoviePresenter> implements
        BaseQuickAdapter.RequestLoadMoreListener, TaskDetailContract.View<MovieEntity>, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.wsrl_refresh)
    SwipeRefreshLayout mRefreshView;

    private RecyclerViewBaseAdapter mAdapter;
    private List<MultipleItemEntity> mMovieData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_support_multiple_layout;
    }

    @Override
    protected void initUI() {
        mMovieData = new ArrayList<>();
        mAdapter = new RecyclerViewBaseAdapter(mMovieData);
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
            }
        });
        mRefreshView.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtils.i("页面初始化：" + this.getClass().getName());
        mPresenter.subscribe();
    }

    @Override
    protected MoviePresenter createPresenter() {
        return new MoviePresenter(ApiService.MOVIE_TYPE_NEWEST, this);
    }

    @Override
    public void showMovieData(List<MovieEntity> list) {

        for (MovieEntity movieEntity : list) {
            mMovieData.add(new MultipleItemEntity(MultipleItemEntity.GRID, 1, movieEntity));
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMoreData(new OnLoadCompleteListener<List<MovieEntity>>() {
            @Override
            public void onSuccess(List<MovieEntity> movieEntities) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshData(new OnLoadCompleteListener<List<MovieEntity>>() {
            @Override
            public void onSuccess(List<MovieEntity> movieEntities) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    @Override
    public void addRefreshData(List<MovieEntity> list) {

    }

    @Override
    public void addMoreData(List<MovieEntity> list) {

    }

    @Override
    public void showLoadError(String ErrorType) {

    }

    @Override
    public void showLoading(boolean active) {
        LogUtils.i("加载中：" + active);
    }


}