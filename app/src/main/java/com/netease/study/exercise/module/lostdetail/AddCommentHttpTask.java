package com.netease.study.exercise.module.lostdetail;

import com.netease.study.exercise.net.MultiPartRequest;

import static com.netease.study.exercise.net.HttpMethod.POST;

/**
 * Created by vincent on 05/12/2016.
 */

public class AddCommentHttpTask extends MultiPartRequest {
    public AddCommentHttpTask(String content, int id, int replyCommentId) {
        mBodyMap.put("content", content);
        mBodyMap.put("infoType", "0");
        mBodyMap.put("infoId", String.valueOf(id));
        mBodyMap.put("commentId", String.valueOf(replyCommentId));
    }

    @Override
    public String getApi() {
        return "comment/addComment";
    }

    @Override
    public int getHttpMethod() {
        return POST;
    }

    @Override
    public Class getModelClass() {
        return null;
    }
}
