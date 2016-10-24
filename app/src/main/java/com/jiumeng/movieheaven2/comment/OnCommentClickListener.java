package com.jiumeng.movieheaven2.comment;

import android.view.View;

import com.jiumeng.movieheaven2.entity.CommentEntity;


/**
 * Created by JuQiu
 * on 16/6/21.
 */

public interface OnCommentClickListener {
    void onClick(View view, CommentEntity comment);
}
