package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 走失信息对应的评论
 */
public class CommentBean implements Serializable {
    private static final long serialVersionUID = 1968522688923666664L;

    private Integer mId;// 评论编号
    private Integer mUserId;//发起该评论的用户编号
    private String mUserName;//发起该评论的用户名
    private String mAvatar;//发起评论的用户图像
    private String mContent;//评论内容
    private Date mCreateTime;//评论时间
    private Integer mCommentId;//评论该条评论的评论ID

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        this.mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public Date getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(Date createTime) {
        this.mCreateTime = createTime;
    }

    public Integer getCommentId() {
        return mCommentId;
    }

    public void setCommentId(Integer commentId) {
        this.mCommentId = commentId;
    }
}
