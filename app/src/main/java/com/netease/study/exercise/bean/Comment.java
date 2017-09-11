package com.netease.study.exercise.bean;

import java.io.Serializable;

/**
 * Created by netease on 15/7/28.
 * Beifeng bwifeng@163.com
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 786866126800547593L;
    /**
     * id                   int                           评论编号
     * <p>
     * userId               int                           发起评论的用户编号
     * <p>
     * userName             String                        发起评论的用户名
     * <p>
     * avatar               String                        发起评论的用户图像
     * <p>
     * content              String                        评论内容
     * <p>
     * createTime           Date                          评论时间
     * <p>
     * commentId            int                           评论该条评论的ID
     * <p>
     * photoName            List<String>                  评论图片列表
     */


    private int mId;
    private int mUserId;
    private String mUserName;
    private String mAvatar;
    private String mContent;
    private String mCreateTime;
    private int mCommentId;
    private String mReplyName;
    private String mPhotoName;


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
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

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        this.mCreateTime = createTime;
    }

    public int getCommentId() {
        return mCommentId;
    }

    public void setCommentId(int commentId) {
        this.mCommentId = commentId;
    }

    public String getReplyName() {
        return mReplyName;
    }

    public void setReplyName(String replyName) {
        this.mReplyName = replyName;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
    }
}
