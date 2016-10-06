package com.jiumeng.movieheaven2.fragment.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiumeng.movieheaven2.R;

/**
 * * 推荐页面的viewpager的 推荐电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class RecommendVpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_information,null);
    }
//    @Override
//    protected int getMovieType() {
//        return getArguments().getInt("movieType");
//    }
//
//    @Override
//    public List<MultipleItem> setMultipeItem(ArrayList<MovieDao> data) {
//        return null;
//    }
//
//    @Override
//    public List<MultipleItem> setMultipeItem2(ArrayList<ArrayList<MovieDao>> data) {
//        List<MultipleItem> initData = new ArrayList<>();
//        for (ArrayList<MovieDao> list : data) {
//            initData.add(new MultipleItem(MultipleItem.RECOMMEND,1,list));
//        }
//        return initData;
//    }
}