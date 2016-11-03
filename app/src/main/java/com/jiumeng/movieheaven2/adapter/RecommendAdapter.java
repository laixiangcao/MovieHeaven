package com.jiumeng.movieheaven2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by jiumeng on 2016/10/25.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder>{
    private final ArrayList<MovieEntity> dataList;

    public RecommendAdapter(ArrayList<MovieEntity> list){
        this.dataList=list;
    }


    @Override
    public RecommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.inflate(R.layout.item_recommend_image);
        return new RecommendHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendHolder holder, int position) {
        if (dataList.get(position).jpgList!=null&&dataList.get(position).jpgList.size()>0){
            Glide.with(UIUtils.getContext()).load(dataList.get(position).jpgList.get(0)).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class RecommendHolder extends RecyclerView.ViewHolder{
       ImageView img;
        RecommendHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
