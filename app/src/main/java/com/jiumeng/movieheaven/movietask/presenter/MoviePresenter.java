package com.jiumeng.movieheaven.movietask.presenter;


import com.jiumeng.movieheaven.entity.MovieEntity;
import com.jiumeng.movieheaven.movietask.TaskDetailContract;
import com.jiumeng.movieheaven.movietask.base.BasePresenter;
import com.jiumeng.movieheaven.movietask.interfaces.OnLoadCompleteListener;
import com.jiumeng.movieheaven.movietask.model.MovieModelImpl;

import java.util.List;


/**
 * Created by jiumeng on 2016/12/5.
 */

public class MoviePresenter extends BasePresenter<TaskDetailContract.View<MovieEntity>> implements TaskDetailContract.Presenter<List<MovieEntity>> {


    private final MovieModelImpl movieModel;

    public MoviePresenter(int movieType, TaskDetailContract.View<MovieEntity> mvpView) {
        super(movieType, mvpView);
        movieModel = new MovieModelImpl(movieType);
    }

    @Override
    public void subscribe() {
        view.showLoading(true);
        //判断缓存文件是否超出有效时间
//        boolean isInvalid = PrefUtils.getCacheTime(movieType) == -1 &&
//                System.currentTimeMillis() - PrefUtils.getCacheTime(movieType) > ApiService.MOVIE_CACHE_VALID_TIME;
//
//        if (!isInvalid) {
//            LogUtils.i("从本地读取缓存文件");
//            //读取本地缓存文件
//            List<MovieEntity> list = (List<MovieEntity>) PrefUtils.getCache(movieType);
//            if (list != null) {
//                view.showLoading(false);
//                view.showMovieData(list);
//            }
//
//        } else {
//            LogUtils.i("从网络获取数据");
//            loadData();
//        }
        loadData();

    }


    @Override
    public void loadData() {
        addSubscription(movieModel.loadData(1, new OnLoadCompleteListener<List<MovieEntity>>() {
            @Override
            public void onSuccess(List<MovieEntity> movieEntities) {
                if (movieEntities != null && movieEntities.size() > 0) {
                    view.showLoading(false);
                    view.showMovieData(movieEntities);
                } else {
                    view.showLoading(false);
                    onError(new Throwable("数据为空"));
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showLoading(false);
                view.showLoadError(e.getLocalizedMessage());
            }
        }));
    }

    @Override
    public void refreshData(final OnLoadCompleteListener<List<MovieEntity>> listener) {
        addSubscription(movieModel.loadNewData(new OnLoadCompleteListener<List<MovieEntity>>() {
            @Override
            public void onSuccess(List<MovieEntity> movieEntities) {
                if (movieEntities != null && movieEntities.size() > 0) {
                    listener.onSuccess(movieEntities);
                } else {
                    listener.onError(new Throwable("数据为空"));
                }

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }
        }));
    }

    @Override
    public void loadMoreData(final OnLoadCompleteListener<List<MovieEntity>> listener) {
        addSubscription(movieModel.loadMoreData(new OnLoadCompleteListener<List<MovieEntity>>() {
            @Override
            public void onSuccess(List<MovieEntity> movieEntities) {
                if (movieEntities != null && movieEntities.size() > 0) {
                    listener.onSuccess(movieEntities);
                } else {
                    listener.onError(new Throwable("数据为空"));
                }
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }
        }));
    }

}
