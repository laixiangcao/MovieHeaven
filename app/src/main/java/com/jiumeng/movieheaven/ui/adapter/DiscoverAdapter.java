package com.jiumeng.movieheaven.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.entity.MovieEntity;
import com.jiumeng.movieheaven.utils.TextUtils;
import com.jiumeng.movieheaven.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Item adapter.
 */
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    /**
     * Value for no item_discover_movie_describe expanded.
     */
    public static final int NONE = -1;

    /**
     * Context.
     */
    private final Context context;

    /**
     * Items.
     */
    private final ArrayList<MovieEntity> dataList;

    /**
     * On item_discover_movie_describe size listener.
     */
    private OnItemSizeListener onItemSizeListener;

    /**
     * On item_discover_movie_describe click listener.
     */
    private OnItemClickListener onItemClickListener;

    /**
     * Current expanded item_discover_movie_describe.
     */
    private int currentExpandedItem = -1;

    /**
     * Current expanded view holder.
     */
    private ViewHolder currentExpandedViewHolder;

    /**
     * Item adapter's constructor.
     *
     * @param context  Context.
     * @param dataList Items to display.
     */
    public DiscoverAdapter(@NonNull final Context context, @NonNull final ArrayList<MovieEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_discover_movie_describe, parent, false);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onItemSizeListener != null) {
                    onItemSizeListener.onItemSize(view.getMeasuredWidth());
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // - If holder is reuse by an other position, invalidate current expanded view holder.
        if (currentExpandedViewHolder == viewHolder) {
            currentExpandedViewHolder = null;
        }

        // - If user returns to expanded item_discover_movie_describe, update current expanded view holder.
        if (currentExpandedItem == position) {
            currentExpandedViewHolder = viewHolder;
        }

        if (dataList.get(position) != null && dataList.size() > 0) {
            String jpgUrl = TextUtils.url2Id(dataList.get(position).url, true);
            Glide.with(UIUtils.getContext()).load(jpgUrl).error(R.drawable.default_movie_image).into(viewHolder.picture);
        }


        // - Bind events.
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position, viewHolder);
                }
            }
        });

        // - Expand/collapse if necessary.
        if (currentExpandedItem == position) {
            expandView(viewHolder, false);
        } else {
            collapseView(viewHolder, false);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Defines on item_discover_movie_describe size listener.
     *
     * @param onItemSizeListener On item_discover_movie_describe size listener.
     */
    public void setOnItemSizeListener(final OnItemSizeListener onItemSizeListener) {
        this.onItemSizeListener = onItemSizeListener;
    }

    /**
     * Defines on item_discover_movie_describe click listener.
     *
     * @param onItemClickListener On click listener.
     */
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Expands item_discover_movie_describe.
     *
     * @param position   Item's position.
     * @param viewHolder Item's view holder.
     */
    public void expandItem(final int position, final ViewHolder viewHolder) {
        if (currentExpandedItem != NONE && currentExpandedViewHolder != null) {
            collapseItem(currentExpandedViewHolder);
        }
        currentExpandedItem = position;
        currentExpandedViewHolder = viewHolder;

        expandView(viewHolder, true);
    }

    /**
     * Expands item_discover_movie_describe's view.
     *
     * @param viewHolder Item's view holder.
     * @param animate    Animate.
     */
    private void expandView(final ViewHolder viewHolder, final boolean animate) {
        if (viewHolder.moreLayout.getVisibility() != View.VISIBLE) {
            viewHolder.moreLayout.setVisibility(View.VISIBLE);
            final Animation scaleMoreAnimation = AnimationUtils.loadAnimation(context, R.anim.reveal_more);
            if (!animate) {
                scaleMoreAnimation.setDuration(0);
            }
            viewHolder.moreLayout.startAnimation(scaleMoreAnimation);
            viewHolder.pictureLayout.animate()
                    .translationY(-context.getResources().getDimensionPixelSize(R.dimen.picture_translate_distance))
                    .setDuration(scaleMoreAnimation.getDuration())
                    .setInterpolator(scaleMoreAnimation.getInterpolator())
                    .start();
        }
    }

    /**
     * Collapses item_discover_movie_describe.
     *
     * @param viewHolder Item's view holder.
     */
    public void collapseItem(final ViewHolder viewHolder) {
        currentExpandedItem = -1;
        collapseView(viewHolder, true);
    }

    /**
     * Collapse item_discover_movie_describe's view.
     *
     * @param viewHolder Item's view holder.
     * @param animate    Animate.
     */
    private void collapseView(final ViewHolder viewHolder, final boolean animate) {
        if (viewHolder.moreLayout.getVisibility() != View.INVISIBLE) {
            viewHolder.moreLayout.setVisibility(View.INVISIBLE);
            final Animation scaleMoreAnimation = AnimationUtils.loadAnimation(context, R.anim.reveal_more);
            if (!animate) {
                scaleMoreAnimation.setDuration(0);
            }
            viewHolder.pictureLayout.animate()
                    .translationY(0)
                    .setDuration(scaleMoreAnimation.getDuration())
                    .setInterpolator(scaleMoreAnimation.getInterpolator())
                    .start();
            scaleMoreAnimation.setInterpolator(new ReverseInterpolator(scaleMoreAnimation.getInterpolator()));
            viewHolder.moreLayout.startAnimation(scaleMoreAnimation);
        }
    }

    /**
     * Determines if an item_discover_movie_describe is collapsed.
     *
     * @param position Item position.
     * @return TRUE if collapsed.
     */
    public boolean isCollapsed(final int position) {
        return currentExpandedItem != position;
    }


    /**
     * On item_discover_movie_describe size listener interface.
     */
    public interface OnItemSizeListener {
        void onItemSize(int size);
    }

    /**
     * On item_discover_movie_describe click listener interface.
     */
    interface OnItemClickListener {
        void onClick(int position, ViewHolder viewHolder);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindView(R.id.tv_update_time)
        TextView tvUpdateTime;
        @BindView(R.id.rb_grade)
        RatingBar rbGrade;
        @BindView(R.id.more_layout)
        CardView moreLayout;
        @BindView(R.id.picture)
        ImageView picture;
        @BindView(R.id.picture_layout)
        CardView pictureLayout;

        final View rootView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    private class ReverseInterpolator implements Interpolator {

        /**
         * Interpolator to reverse.
         */
        private final Interpolator mInterpolator;

        /**
         * Reverse interpolator constructor.
         *
         * @param interpolator Interpolator to reverse.
         */
        ReverseInterpolator(@NonNull final Interpolator interpolator) {
            this.mInterpolator = interpolator;
        }

        @Override
        public float getInterpolation(float value) {
            return 1 - mInterpolator.getInterpolation(value);
        }
    }
}
