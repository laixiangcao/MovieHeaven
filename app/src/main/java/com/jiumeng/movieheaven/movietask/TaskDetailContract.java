package com.jiumeng.movieheaven.movietask;


import com.jiumeng.movieheaven.movietask.interfaces.MovieModel;
import com.jiumeng.movieheaven.movietask.interfaces.MoviePresenter;
import com.jiumeng.movieheaven.movietask.interfaces.MovieView;
import com.jiumeng.movieheaven.movietask.interfaces.OnLoadCompleteListener;

import java.util.List;

import rx.Subscription;


/**
 * This specifies the contract between the view and the presenter.
 */
public interface TaskDetailContract {

    interface View<T> extends MovieView<T> {

        void addRefreshData(List<T> list);

        void addMoreData(List<T> list);

    }

    interface Model<T> extends MovieModel<T> {
        Subscription loadMoreData(OnLoadCompleteListener<T> listener);
        Subscription loadNewData(OnLoadCompleteListener<T> listener);
    }

    interface Presenter<T> extends MoviePresenter {
        void loadData();

        void refreshData(OnLoadCompleteListener<T> listener);

        void loadMoreData(OnLoadCompleteListener<T> listener);
    }

}
