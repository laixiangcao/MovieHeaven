package com.jiumeng.movieheaven2.entity;


import java.util.List;

import cn.bmob.v3.BmobObject;

public class CommentEntity extends BmobObject {

    private String movieName;
    private Boolean existReply;
    private String author;
    private String content;
    private String AuthorPortrait;
    private List<String> replyList;

    @Override
    public void setTableName(String tableName) {
        super.setTableName("movie_comment");
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Boolean getExistReply() {
        return existReply;
    }

    public void setExistReply(Boolean existReply) {
        this.existReply = existReply;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorPortrait() {
        return AuthorPortrait;
    }

    public void setAuthorPortrait(String authorPortrait) {
        this.AuthorPortrait = authorPortrait;
    }

    public List<String> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<String> replyList) {
        this.replyList = replyList;
    }


}
