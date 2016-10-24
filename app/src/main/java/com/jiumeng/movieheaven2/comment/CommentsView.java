package com.jiumeng.movieheaven2.comment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.entity.CommentEntity;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by JuQiu
 * on 16/6/12.
 */

public class CommentsView extends LinearLayout implements View.OnClickListener {
    private long mId;
    private int mType;
    private TextView mTitle;//标题： 相关评论
    private TextView mSeeMore;//查看更多
    private LinearLayout mLayComments;//用于评论item存放的布局

    public CommentsView(Context context) {
        super(context);
        init();
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.lay_detail_comment_layout, this, true);

        mTitle = (TextView) findViewById(R.id.tv_blog_detail_comment);
        mLayComments = (LinearLayout) findViewById(R.id.lay_blog_detail_comment);
        mSeeMore = (TextView) findViewById(R.id.tv_see_more_comment);
    }

    public void setTitle(String title) {
        if (!MyTextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    public void initComment(String movieName) {
        BmobQuery<CommentEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("movieName", movieName);
        //默认返回10条数据
        //执行查询方法

        query.findObjects(new FindListener<CommentEntity>() {
            @Override
            public void done(List<CommentEntity> commentList, BmobException e) {
                if (e == null) {
                    UIUtils.showToast("查询成功：共" + commentList.size() + "条数据。");
                    for (CommentEntity comment : commentList) {
                        //获得playerName的信息
                        comment.getAuthor();
                        //获得数据的objectId信息
                        comment.getAuthorPortrait();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        comment.getCreatedAt();
                        comment.getContent();
                        comment.getReplyList();
                    }
                    addComment(commentList);
                    setTitle("评论(" + commentList.size() + ")");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    setVisibility(GONE);
                }
            }
        });
    }

    public void addComment(List<CommentEntity> comments) {
        int commentTotal = comments.size();
        if (commentTotal < 10) {
            mSeeMore.setVisibility(GONE);
            if (commentTotal == 0) {
                setVisibility(GONE);
            }
        } else {
            mSeeMore.setVisibility(VISIBLE);
            mSeeMore.setOnClickListener(this);
        }

        int clearLine = comments.size() - 1;
        for (CommentEntity comment : comments) {
            if (comment == null || MyTextUtils.isEmpty(comment.getObjectId()) || MyTextUtils.isEmpty(comment.getAuthor())) {
                continue;
            }
            ViewGroup lay = addComment(false, comment, new OnCommentClickListener() {
                @Override
                public void onClick(View view, CommentEntity comment) {
                    UIUtils.showToast("评论");
                }
            });
            if (clearLine <= 0) {
                lay.findViewById(R.id.line).setVisibility(View.INVISIBLE);
            } else {
                clearLine--;
            }
        }
    }

    @Override
    public void onClick(View v) {
        UIUtils.showToast("查看更多");
    }

    public ViewGroup addComment(boolean first, final CommentEntity comment, final OnCommentClickListener onCommentClickListener) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup lay = (ViewGroup) inflater.inflate(R.layout.lay_comment_item, null, false);
        ImageView ivAvatar = (ImageView) lay.findViewById(R.id.iv_avatar);

        ((TextView) lay.findViewById(R.id.tv_name)).setText(comment.getAuthor());

        TextView content = ((TextView) lay.findViewById(R.id.tv_content));
        content.setText(comment.getContent());

        ((TextView) lay.findViewById(R.id.tv_pub_date)).setText(comment.getCreatedAt());

        lay.findViewById(R.id.btn_comment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentClickListener.onClick(v, comment);
            }
        });

        if (first)
            mLayComments.addView(lay, 0);
        else
            mLayComments.addView(lay);

        return lay;
    }
}
