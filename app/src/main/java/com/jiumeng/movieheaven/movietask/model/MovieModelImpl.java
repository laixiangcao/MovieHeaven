package com.jiumeng.movieheaven.movietask.model;


import android.util.Log;

import com.jiumeng.movieheaven.entity.MovieEntity;
import com.jiumeng.movieheaven.movietask.TaskDetailContract;
import com.jiumeng.movieheaven.movietask.interfaces.OnLoadCompleteListener;
import com.jiumeng.movieheaven.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Subscription;

/**
 * Created by jiumeng on 2016/12/29.
 */

public class MovieModelImpl implements TaskDetailContract.Model<List<MovieEntity>> {
    private final int mMovieType;
    private int page = 1;
    private static final int MAX_PAGES = 28;

    public MovieModelImpl(int movieType) {
        this.mMovieType = movieType;
    }

    @Override
    public Subscription loadMoreData(OnLoadCompleteListener<List<MovieEntity>> listener) {
        if (++page < MAX_PAGES) {
            return loadData(page, listener);
        }
        return null;
    }

    @Override
    public Subscription loadNewData(OnLoadCompleteListener<List<MovieEntity>> listener) {
        return loadData(1, listener);
    }


    @Override
    public Subscription loadData(int page, final OnLoadCompleteListener<List<MovieEntity>> listener) {
        final List<MovieEntity> movieList = new ArrayList<>();
        BmobQuery<MovieEntity> query = new BmobQuery<>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        int limit=15;
        query.setLimit(limit);
        query.order("-updatetime");
        //执行查询方法
        return query.findObjects(new FindListener<MovieEntity>() {
            @Override
            public void done(List<MovieEntity> object, BmobException e) {
                if (e == null) {
                    LogUtils.i("查询成功：共" + object.size() + "条数据。");
                    for (MovieEntity movieEntity : object) {
                        //获得playerName的信息
                        movieList.add(movieEntity);
                        if(movieList.size()==15){
                            listener.onSuccess(movieList);
                        }
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    listener.onError(e);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                listener.onSuccess(movieList);
                LogUtils.i("加载完成 共：" + movieList.size());
            }
        });
    }

}
