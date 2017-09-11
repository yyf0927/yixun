package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

public class MoreCommentBean implements Serializable {
    private static final long serialVersionUID = 7358543598519087343L;

    private List<Comment> mCommentList;

    public List<Comment> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.mCommentList = commentList;
    }
}