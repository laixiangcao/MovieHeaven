package com.jiumeng.movieheaven2.fragment.viewpager;


import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.impl.Impl2MultipleLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * * 推荐页面的viewpager的 推荐电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class RecommendVpFragment extends Impl2MultipleLayoutFragment {

    @Override
    protected int getMovieType() {
        return getArguments().getInt("movieType");
    }


    @Override
    public List<MultipleItemEntity> setMultipeItem2(ArrayList<ArrayList<MovieEntity>> data) {
        ArrayList<MultipleItemEntity> list = new ArrayList<>();
        for (ArrayList<MovieEntity> movieEntities : data) {
            list.add(new MultipleItemEntity(MultipleItemEntity.RECOMMEND, movieEntities));
        }
        System.out.println("aaa"+list.size());
        return list;
    }
}