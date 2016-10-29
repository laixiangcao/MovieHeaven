package com.jiumeng.movieheaven2.fragment.tabhost;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.MovieDetailsActivity;
import com.jiumeng.movieheaven2.adapter.ItemAdapter;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseLoadFragment;
import com.jiumeng.movieheaven2.network.MyStringCallback;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * tabhost标签中的 发现页面
 * Created by jiumeng on 2016/9/24.
 */
public class DiscoverFragment extends BaseLoadFragment {

    @BindView(R.id.recy_movies)
    RecyclerView recyMovies;


    private ArrayList<MovieEntity> dataList;

    @Override
    protected void initPageData(boolean isFirstLoad) {

        if (isFirstLoad){
            dataList= (ArrayList<MovieEntity>) PrefUtils.readObject("MovieType:discover");
            if (dataList!=null&&dataList.size()>0){
                loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
                return;
            }
        }

        NetWorkApi.getMovieDetailInfo(NetWorkApi.HOST, this, new MyStringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                UIUtils.showToast(e.getLocalizedMessage());
                loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
            }

            @Override
            public void onResponse(String response, int id) {
                ArrayList<ArrayList<String>> homeUrl = ProcessData.getHomeUrl(response);
                if (homeUrl != null) {
                    final ArrayList<String> newUrl = homeUrl.get(0);
                    dataList = new ArrayList<>();
                    for (final String url : newUrl) {
                        NetWorkApi.getMovieDetailInfo(url, this, new MyStringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println(url + "[" + e.getMessage() + "]");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                MovieEntity movieEntity = new MovieEntity();
                                movieEntity.url = url;
                                movieEntity = ProcessData.parseMovieDetails(response, movieEntity, true);
                                dataList.add(movieEntity);
                                if (dataList.size() == newUrl.size()) {
                                    PrefUtils.saveObject("MovieType:discover", dataList);
                                    loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
                                }

                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    protected View onCreateSuccessView() {
        View view = mLayoutInflater.inflate(R.layout.fragment_discover, null);
        ButterKnife.bind(this, view);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyMovies.setLayoutManager(linearLayoutManager);
        new LinearSnapHelper().attachToRecyclerView(recyMovies);
        final ItemAdapter itemAdapter = new ItemAdapter(getContext(), dataList);
        recyMovies.setAdapter(itemAdapter);

        // - Center recycler view first item_discover_movie_describe.
        itemAdapter.setOnItemSizeListener(new ItemAdapter.OnItemSizeListener() {
            @Override
            public void onItemSize(final int size) {
                itemAdapter.setOnItemSizeListener(null);
                final int padding = (recyMovies.getMeasuredWidth() - size) / 2;
                recyMovies.setPadding(padding, 0, padding, 0);
                recyMovies.smoothScrollToPosition(0);
            }
        });

        // - On item_discover_movie_describe click...
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final int position, final ItemAdapter.ViewHolder viewHolder) {

                MovieEntity movie = dataList.get(position);
                if (!TextUtils.isEmpty(movie.grade)) {
                    try {
                        float grade = Float.parseFloat(movie.grade) / 2;
                        viewHolder.rbGrade.setRating(grade);
                    } catch (NumberFormatException e) {
                        viewHolder.rbGrade.setRating(0);
                    }
                }
                viewHolder.tvMovieName.setText(movie.minName);
                viewHolder.tvUpdateTime.setText("更新时间："+movie.updatetime);

                // - If the clicked item_discover_movie_describe is not centered, scroll to it.
                final int currentPosition = (linearLayoutManager.findFirstVisibleItemPosition() + linearLayoutManager.findLastVisibleItemPosition()) / 2;
                if (currentPosition != position) {
                    recyMovies.smoothScrollToPosition(position);
                }

                // - Else if collapsed, expand it.
                else if (itemAdapter.isCollapsed(position)) {
                    itemAdapter.expandItem(position, viewHolder);
                }

                // - Else (item_discover_movie_describe centered and expanded), navigate to details.
                else {
                    final ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), viewHolder.picture, viewHolder.picture.getTransitionName());
                    final Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, dataList.get(position));
                    startActivity(intent, options.toBundle());
                }

            }
        });
        return view;
    }


}
