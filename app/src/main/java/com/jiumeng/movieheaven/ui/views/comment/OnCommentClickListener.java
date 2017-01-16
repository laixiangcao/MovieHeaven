package com.jiumeng.movieheaven.ui.views.comment;

import android.view.View;

import com.jiumeng.movieheaven.entity.CommentEntity;


/**
 * Created by JuQiu
 * on 16/6/21.
 */

public interface OnCommentClickListener {
    void onClick(View view, CommentEntity comment);
}
