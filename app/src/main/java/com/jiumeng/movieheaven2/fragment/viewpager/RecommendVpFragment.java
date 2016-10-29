package com.jiumeng.movieheaven2.fragment.viewpager;


import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.fragment.impl.Impl2MultipleLayoutFragment;
import com.jiumeng.movieheaven2.utils.UIUtils;

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
        String[] stringArray = UIUtils.getStringArray(R.array.recommend_sub_names);
        ArrayList<MultipleItemEntity> list = new ArrayList<>();
        int i = 0;
        for (ArrayList<MovieEntity> movieEntities : data) {
            MultipleItemEntity itemEntity = new MultipleItemEntity(MultipleItemEntity.RECOMMEND, movieEntities);
            itemEntity.setTitle(stringArray[i]);
            list.add(itemEntity);
            i++;
        }
        return list;
    }
}