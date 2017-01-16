package com.jiumeng.movieheaven.ui.adapter;

import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.entity.MovieEntity;
import com.jiumeng.movieheaven.entity.MultipleItemEntity;
import com.jiumeng.movieheaven.utils.TextUtils;
import com.jiumeng.movieheaven.utils.UIUtils;

import java.util.List;


/**
 * Created by jiumeng on 2016/9/27.
 */

public class RecyclerViewBaseAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity> {


    public RecyclerViewBaseAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(MultipleItemEntity.LIST, R.layout.item_list_movie);
        addItemType(MultipleItemEntity.GRID, R.layout.item_grid_movie);
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultipleItemEntity multipleItem) {

        switch (baseViewHolder.getItemViewType()) {
            case MultipleItemEntity.LIST:
                final MovieEntity data = multipleItem.getData();
                baseViewHolder.setText(R.id.tv_name, data.name);
                baseViewHolder.setText(R.id.tv_type, "类型：" + data.category);
                baseViewHolder.setText(R.id.tv_update, "更新时间：" + data.updatetime);
                baseViewHolder.setText(R.id.tv_grade, "评分：" + data.grade);
                break;
            case MultipleItemEntity.GRID:
                MovieEntity data2 = multipleItem.getData();
                ImageView iv_img = baseViewHolder.getView(R.id.iv_img);

                String jpgUrl = TextUtils.id2Url(data2.id, true);
                Glide.with(UIUtils.getContext()).load(jpgUrl).error(R.drawable.default_movie_image).into(iv_img);

                baseViewHolder.setText(R.id.tv_name, data2.minName);
                baseViewHolder.setText(R.id.tv_update, data2.updatetime);
                RatingBar ratingBar = baseViewHolder.getView(R.id.rb_grade);

                if (!android.text.TextUtils.isEmpty(data2.grade)) {
                    try {
                        float grade = Float.parseFloat(data2.grade) / 2;
                        ratingBar.setRating(grade);
                    } catch (NumberFormatException e) {
                        ratingBar.setRating(0);
                    }
                }
                break;
            case MultipleItemEntity.RECOMMEND:

                break;
        }
    }

}
