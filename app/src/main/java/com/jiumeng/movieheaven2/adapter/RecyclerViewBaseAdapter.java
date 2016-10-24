package com.jiumeng.movieheaven2.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.entity.MultipleItemEntity;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import java.util.ArrayList;
import java.util.List;




/**
 * Created by jiumeng on 2016/9/27.
 */

public class RecyclerViewBaseAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity> {

    private int y;

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
                baseViewHolder.setText(R.id.tv_type, "类型：" + data.category);
                baseViewHolder.setText(R.id.tv_update, "更新时间：" + data.updatetime);
                baseViewHolder.setText(R.id.tv_grade, "评分：" + data.grade);
                break;
            case MultipleItemEntity.GRID:
                MovieEntity data2 = multipleItem.getData();
                ImageView iv_img = baseViewHolder.getView(R.id.iv_img);
                Glide.with(UIUtils.getContext()).load(MyTextUtils.id2Url(data2.id)).into(iv_img);
                baseViewHolder.setText(R.id.tv_name, data2.minName);
                baseViewHolder.setText(R.id.tv_update, data2.updatetime);
                RatingBar ratingBar = baseViewHolder.getView(R.id.rb_grade);

                if (!TextUtils.isEmpty(data2.grade)) {
                    try {
                        float grade = Float.parseFloat(data2.grade) / 2;
                        ratingBar.setRating(grade);
                    } catch (NumberFormatException e) {
                        ratingBar.setRating(0);
                    }
                }
                break;
            case MultipleItemEntity.RECOMMEND:
                final ArrayList<MovieEntity> dataList = multipleItem.getDataList();
                final LinearLayout linearLayout = baseViewHolder.getView(R.id.ll_content);
                TextView tvChange = baseViewHolder.getView(R.id.tv_change);
                ImageView ivChange = baseViewHolder.getView(R.id.iv_change);
                TextView tvMore = baseViewHolder.getView(R.id.tv_more);
                addView(dataList, linearLayout, 0);
                y = 3;
                tvChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.removeAllViews();
                        addView(dataList, linearLayout, y +3);
                    }
                });
                break;
        }
    }

    private void addView(ArrayList<MovieEntity> dataList, LinearLayout linearLayout, int i) {
        for (int j = i; j < i + 3; j++) {
            MovieEntity movieDao = dataList.get(i);

            View view = UIUtils.inflate(R.layout.item_grid_movie);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            RatingBar rbGrade = (RatingBar) view.findViewById(R.id.rb_grade);
            TextView tvUpdate = (TextView) view.findViewById(R.id.tv_update);

            Glide.with(UIUtils.getContext()).load(MyTextUtils.id2Url(movieDao.id)).into(imageView);
            tvName.setText(movieDao.minName);
            tvUpdate.setText(movieDao.updatetime);

            if (!TextUtils.isEmpty(movieDao.grade)) {
                try {
                    float grade = Float.parseFloat(movieDao.grade) / 2;
                    rbGrade.setRating(grade);
                } catch (NumberFormatException e) {
                    rbGrade.setRating(0);
                }
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            int margin = UIUtils.dip2px(5);
            layoutParams.setMargins(margin, margin, margin, margin);
            linearLayout.addView(view, layoutParams);

        }
    }
}
