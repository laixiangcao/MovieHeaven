package com.jiumeng.movieheaven2.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiumeng on 2016/9/27.
 */

public class RecyclerViewBaseAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity> {
    public RecyclerViewBaseAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(MultipleItemEntity.LIST, R.layout.item_list_movie);
        addItemType(MultipleItemEntity.GRID, R.layout.item_grid_movie);
        addItemType(MultipleItemEntity.RECOMMEND, R.layout.item_recommend_movie);
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultipleItemEntity multipleItem) {

        switch (baseViewHolder.getItemViewType()) {
            case MultipleItemEntity.LIST:
                MovieEntity data = multipleItem.getData();
                baseViewHolder.setText(R.id.tv_name, data.name);
                baseViewHolder.setText(R.id.tv_type, "类型："+data.category);
                baseViewHolder.setText(R.id.tv_update, "更新时间："+data.updatetime);
                baseViewHolder.setText(R.id.tv_grade, "评分："+data.grade);
                break;
            case MultipleItemEntity.GRID:
                MovieEntity data2 = multipleItem.getData();
                ImageView iv_img=baseViewHolder.getView(R.id.iv_img);
                String url= NetWorkApi.MYHOST+"/image/"+data2.id+".jpg_movieheaven";
                Glide.with(UIUtils.getContext()).load(url).into(iv_img);
                baseViewHolder.setText(R.id.tv_name, data2.minName);
                baseViewHolder.setText(R.id.tv_update, data2.updatetime);
//                baseViewHolder.setText(R.id.tv_grade, "评分:"+data.grade);
                RatingBar ratingBar = baseViewHolder.getView(R.id.rb_grade);

                if (!TextUtils.isEmpty(data2.grade)) {
                    try {
                        float grade = Float.parseFloat(data2.grade) / 2;
                        ratingBar.setRating(grade);
                    }catch (NumberFormatException e){
                        ratingBar.setRating(0);
                    }
                }
                break;
            case MultipleItemEntity.RECOMMEND:
                ArrayList<MovieEntity> dataList = multipleItem.getDataList();
                for (MovieEntity movieDao : dataList) {
                    System.out.println("aaa:"+movieDao.name);
                }
                break;
        }
    }
}
