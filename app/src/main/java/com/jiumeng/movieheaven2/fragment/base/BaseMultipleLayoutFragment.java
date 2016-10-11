package com.jiumeng.movieheaven2.fragment.base;

import android.support.annotation.ColorInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiumeng on 2016/10/4.
 */

public abstract class BaseMultipleLayoutFragment extends BaseLoadFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindColor(R.color.white)
    @ColorInt
    int refreshColor;
    @BindView(R.id.rv_list)
    protected
    RecyclerView mRecyclerView;
    @BindView(R.id.wsrl_refresh)
    protected
    SwipeRefreshLayout mSwipRefresh;


    @Override
    protected View onCreateSuccessView() {
        View rootView = mLayoutInflater.inflate(R.layout.fragment_base_support_multiple_layout, null);
        ButterKnife.bind(this, rootView);
        initSwipRefreshView();
        initRecyclerView();
        return rootView;
    }

    protected abstract void initRecyclerView();

    @Override
    protected abstract void initPageData(boolean isFirstLoad) ;

    protected void initSwipRefreshView(){
        mSwipRefresh.setOnRefreshListener(this);
        mSwipRefresh.setColorSchemeColors(refreshColor);
        int[] colors = {R.color.refresh1, R.color.refresh2, R.color.refresh3};
        mSwipRefresh.setColorSchemeResources(colors);//设置进度动画的颜色。
    }



    protected abstract int getMovieType();

    /**
     * 设置多布局
     * @return
     */
    public abstract List<MultipleItem> setMultipeItem(ArrayList<MovieDao> data);


}
