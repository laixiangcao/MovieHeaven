package com.jiumeng.movieheaven2.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiumeng on 2016/9/27.
 */

public class RecyclerViewBaseAdapter extends BaseMultiItemQuickAdapter<MultipleItem> {
    public RecyclerViewBaseAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.LIST, R.layout.item_list_movie);
        addItemType(MultipleItem.GRID, R.layout.item_grid_movie);
        addItemType(MultipleItem.RECOMMEND, R.layout.item_recommend_movie);
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultipleItem multipleItem) {

        switch (baseViewHolder.getItemViewType()) {
            case MultipleItem.LIST:
                MovieDao data = multipleItem.getData();
                baseViewHolder.setText(R.id.tv_name, data.minName);
                baseViewHolder.setText(R.id.tv_type, data.category);
                baseViewHolder.setText(R.id.tv_update, data.updatetime);
                baseViewHolder.setText(R.id.tv_grade, data.grade);
                break;
            case MultipleItem.GRID:
                MovieDao data2 = multipleItem.getData();
                SimpleDraweeView imageView = baseViewHolder.getView(R.id.iv_img);


                if (data2.jpgList != null) {
                    if (data2.jpgList.size() > 0) {
                        Uri uri = Uri.parse(data2.jpgList.get(0));
                        imageView.setImageURI(uri);
                    }
                }
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
            case MultipleItem.RECOMMEND:
                ArrayList<MovieDao> dataList = multipleItem.getDataList();
                for (MovieDao movieDao : dataList) {
                    System.out.println("aaa:"+movieDao.name);
                }
                break;
        }
    }
}
