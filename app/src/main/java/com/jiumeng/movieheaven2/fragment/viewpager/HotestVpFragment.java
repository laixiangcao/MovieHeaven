package com.jiumeng.movieheaven2.fragment.viewpager;

import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.impl.ImplMultipleLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐页面的viewpager的 最新电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class HotestVpFragment extends ImplMultipleLayoutFragment {


    @Override
    protected int getMovieType() {
        return getArguments().getInt("movieType");
    }

    @Override
    public List<MultipleItemEntity> setMultipeItem(ArrayList<MovieEntity> data) {
        List<MultipleItemEntity> initData = new ArrayList<>();
        for (MovieEntity movieDao : data) {
            initData.add(new MultipleItemEntity(MultipleItemEntity.LIST,3,movieDao));
        }
        return initData;
    }
}