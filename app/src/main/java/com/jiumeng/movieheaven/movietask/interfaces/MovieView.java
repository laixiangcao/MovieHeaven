package com.jiumeng.movieheaven.movietask.interfaces;


import java.util.List;

public interface MovieView<T> {

    void showLoadError(String ErrorType);

    void showLoading(boolean active);

    void showMovieData(List<T> list);


}
