package com.jiumeng.movieheaven2.fragment.other;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.fragment.base.BaseLoadFragment;
import com.jiumeng.movieheaven2.views.LoadingPage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索页面
 * Created by jiumeng on 2016/9/24.
 */
public class SearchFragment extends BaseLoadFragment {


    @BindView(R.id.tv_clear_history)
    TextView tvClearHistory;
    @BindView(R.id.recy_hot_search)
    RecyclerView recyHotSearch;


    @Override
    protected View onCreateSuccessView() {
        View view = mLayoutInflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);
        recyHotSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyHotSearch.setHasFixedSize(true);
        return view;
    }

    @Override
    protected void initPageData(boolean isFirstLoad) {
        //获取搜索历史、热门搜索
        initSearchHistory();
        loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
    }

    private void initSearchHistory() {

    }


    @OnClick(R.id.tv_clear_history)
    public void onClick() {
    }

}
