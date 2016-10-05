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
//            if (movieDao.minName.length() > 8) {
//                initData.add(new MultipleItem(MultipleItem.LIST, 2, movieDao));
//            } else {
//                initData.add(new MultipleItem(MultipleItem.LIST, 1, movieDao));
//            }
            initData.add(new MultipleItem(MultipleItem.GRID,1,movieDao));
        }
        return initData;
    }
}
