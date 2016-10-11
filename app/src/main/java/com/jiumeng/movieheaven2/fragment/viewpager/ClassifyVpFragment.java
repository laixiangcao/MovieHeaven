package com.jiumeng.movieheaven2.fragment.viewpager;

import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;
import com.jiumeng.movieheaven2.fragment.impl.ImplMultipleLayoutFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * 推荐页面的viewpager的 经典电影频道页面
 * Created by jiumeng on 2016/9/25.
 */
public class ClassifyVpFragment extends ImplMultipleLayoutFragment {

    @Override
    protected int getMovieType() {
        return getArguments().getInt("movieType");
    }

    @Override
    public List<MultipleItem> setMultipeItem(ArrayList<MovieDao> data) {
        List<MultipleItem> initData = new ArrayList<>();
        for (MovieDao movieDao : data) {
            initData.add(new MultipleItem(MultipleItem.LIST,3,movieDao));
        }
        return initData;
    }
}
