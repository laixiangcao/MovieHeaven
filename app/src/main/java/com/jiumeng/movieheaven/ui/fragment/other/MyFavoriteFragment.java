//package com.jiumeng.movieheaven2.ui.fragment.other;
//
//import android.content.Intent;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.jiumeng.movieheaven2.R;
//import com.jiumeng.movieheaven2.ui.activity.MovieDetailsActivity;
//import com.jiumeng.movieheaven2.ui.adapter.MyFavoriteAdapter;
//import com.jiumeng.movieheaven2.engine.AccountManager;
//import com.jiumeng.movieheaven2.entity.MovieEntity;
//import com.jiumeng.movieheaven2.entity.MyFavoriteEntity;
//import com.jiumeng.movieheaven2.ui.fragment.base.BaseLoadFragment;
//import com.jiumeng.movieheaven2.utils.UIUtils;
//import com.jiumeng.movieheaven2.ui.views.LoadingPage;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
//
///**
// * Created by jiumeng on 2016/10/25.
// */
//
//public class MyFavoriteFragment extends BaseLoadFragment {
//
//    @BindView(R.id.recycler)
//    RecyclerView recycler;
//
//    public List<MyFavoriteEntity> dataList;
//
//    @Override
//    protected View onCreateSuccessView() {
//        View rootView = UIUtils.inflate(R.layout.fragment_my_favorite);
//        ButterKnife.bind(this, rootView);
//        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        recycler.setItemAnimator(new DefaultItemAnimator());
//        MyFavoriteAdapter myFavoriteAdapter = new MyFavoriteAdapter(dataList);
//        recycler.setAdapter(myFavoriteAdapter);
//        myFavoriteAdapter.setOnItemClickListener(new MyFavoriteAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                MyFavoriteEntity myFavoriteEntity = dataList.get(position);
//                MovieEntity movieEntity = new MovieEntity();
//                movieEntity.url = myFavoriteEntity.getMovieUrl();
//                movieEntity.minName = myFavoriteEntity.getMovieName();
//                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
//                intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movieEntity);
//                intent.putExtra(MovieDetailsActivity.EXTRA_NEED_LOAD_DETAIL, true);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(int position) {
//
//            }
//        });
//        return rootView;
//    }
//
//    @Override
//    protected void initPageData(boolean isFirstLoad) {
//        AccountManager.getInstance().seeMyFavorite(getActivity(), new FindListener<MyFavoriteEntity>() {
//            @Override
//            public void done(List<MyFavoriteEntity> list, BmobException e) {
//                if (e == null) {
//
//                    System.out.println("查询成功：共" + list.size() + "条数据。");
//                    if (list.size() == 0) {
//                        loadDataComplete(LoadingPage.ResultState.STATE_EMPTY);
//                    } else {
//                        dataList = list;
//                        loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
//                    }
//
//                } else {
//                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
//                }
//            }
//        });
//    }
//}
