package com.jiumeng.movieheaven2.adapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BaseActivity;
import com.jiumeng.movieheaven2.activity.BlankActivity;
import com.jiumeng.movieheaven2.activity.MovieDetailsActivity;
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


    public RecyclerViewBaseAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(MultipleItemEntity.LIST, R.layout.item_list_movie);
        addItemType(MultipleItemEntity.GRID, R.layout.item_grid_movie);
        addItemType(MultipleItemEntity.RECOMMEND, R.layout.item_recommend_movie_sub);
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
                RecyclerView rcy_recommend = baseViewHolder.getView(R.id.rcy_recommend);
                TextView tv_title = baseViewHolder.getView(R.id.tv_title);
                tv_title.setText(multipleItem.getTitle());
                TextView tv_more = baseViewHolder.getView(R.id.tv_more);
                tv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtils.showToast("查看更多");

                    }
                });
                CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
                final RecommendAdapter recommendAdapter = new RecommendAdapter(dataList);
                rcy_recommend.setLayoutManager(layoutManager);
                rcy_recommend.setAdapter(recommendAdapter);
                rcy_recommend.setOnScrollListener(new CenterScrollListener());
                layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

                    @Override
                    public void onCenterItemChanged(final int adapterPosition) {
                        MovieEntity movie = dataList.get(adapterPosition);
                        System.out.println();
                        TextView tv_movie_name = baseViewHolder.getView(R.id.tv_movie_name);
                        TextView tv_update_time = baseViewHolder.getView(R.id.tv_update_time);
                        RatingBar rb_grade = baseViewHolder.getView(R.id.rb_grade);

                        if (!TextUtils.isEmpty(movie.grade)) {
                            try {
                                float grade = Float.parseFloat(movie.grade) / 2;
                                rb_grade.setRating(grade);
                            } catch (NumberFormatException e) {
                                rb_grade.setRating(0);
                            }
                        }
                        tv_movie_name.setText(movie.minName);
                        tv_update_time.setText("更新时间：" + movie.updatetime);

                    }
                });
                DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                        int position = recyclerView.getChildLayoutPosition(v);
//                        Intent intent = new Intent(BaseActivity.getForegroundActivity(), BlankActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("movie", dataList.get(position));
//                        intent.putExtras(bundle);
//                        BaseActivity.getForegroundActivity().startActivity(intent);

                        Intent intent = new Intent(BaseActivity.getForegroundActivity(), MovieDetailsActivity.class);
                        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, dataList.get(position));
                        intent.putExtra(MovieDetailsActivity.EXTRA_NEED_LOAD_DETAIL,false);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(BaseActivity.getForegroundActivity(), v, "");
                            BaseActivity.getForegroundActivity().startActivity(intent, options.toBundle());
                        }else {
                            BaseActivity.getForegroundActivity().startActivity(intent);
                        }

                    }
                }, rcy_recommend, layoutManager);
                break;
        }
    }

//    private void addView(ArrayList<MovieEntity> dataList, LinearLayout linearLayout, int i) {
//        for (int j = i; j < i + 3; j++) {
//            MovieEntity movieDao = dataList.get(i);
//
//            View view = UIUtils.inflate(R.layout.item_grid_movie);
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
//            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
//            RatingBar rbGrade = (RatingBar) view.findViewById(R.id.rb_grade);
//            TextView tvUpdate = (TextView) view.findViewById(R.id.tv_update);
//
//            Glide.with(UIUtils.getContext()).load(MyTextUtils.id2Url(movieDao.id)).into(imageView);
//            tvName.setText(movieDao.minName);
//            tvUpdate.setText(movieDao.updatetime);
//
//            if (!TextUtils.isEmpty(movieDao.grade)) {
//                try {
//                    float grade = Float.parseFloat(movieDao.grade) / 2;
//                    rbGrade.setRating(grade);
//                } catch (NumberFormatException e) {
//                    rbGrade.setRating(0);
//                }
//            }
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//            layoutParams.weight = 1;
//            int margin = UIUtils.dip2px(5);
//            layoutParams.setMargins(margin, margin, margin, margin);
//            linearLayout.addView(view, layoutParams);
//
//        }
//    }
}
