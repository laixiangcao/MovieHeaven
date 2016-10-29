package com.jiumeng.movieheaven2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.MyFavoriteEntity;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jiumeng on 2016/10/25.
 */
public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.MyFavoriteHolder> {

    private final List<MyFavoriteEntity> dataList;
    private OnItemClickListener mOnItemClickListener;

    public MyFavoriteAdapter(List<MyFavoriteEntity> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyFavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.inflate(R.layout.item_recycler_my_favorite);
        return new MyFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFavoriteHolder holder, final int position) {
        holder.movieName.setText(dataList.get(position).getMovieName());
        holder.update.setText(dataList.get(position).getCreatedAt());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class MyFavoriteHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        TextView update;


        MyFavoriteHolder(View itemView) {
            super(itemView);
            movieName = ButterKnife.findById(itemView, R.id.tv_movie_name);
            update = ButterKnife.findById(itemView, R.id.tv_update);
        }
    }
}
