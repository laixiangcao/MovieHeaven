package com.jiumeng.movieheaven2.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiumeng on 2016/10/22.
 */

public class ReplyEntity extends BmobObject {
    int topicId;
    boolean existReply;
    String author;
    String content;
    String portrait;
}
