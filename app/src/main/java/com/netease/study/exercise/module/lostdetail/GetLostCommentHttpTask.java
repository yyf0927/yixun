package com.netease.study.exercise.module.lostdetail;

import com.netease.study.exercise.bean.MoreCommentBean;
import com.netease.study.exercise.net.FormRequest;

import static com.netease.study.exercise.net.HttpMethod.POST;

/**
 * Created by vincent on 05/12/2016.
 */

public class GetLostCommentHttpTask extends FormRequest {
    private String GET_LOST_COMMENT_PAGE_SIZE = "20";
    private String GET_LOST_COMMENT_INFO_TYPE = "0";

    public GetLostCommentHttpTask(int id, int pageNum) {
        super();

        mBodyMap.put("infoType", GET_LOST_COMMENT_INFO_TYPE);
        mBodyMap.put("infoId", String.valueOf(id));
        mBodyMap.put("pageNum", String.valueOf(pageNum));
        mBodyMap.put("pageSize", GET_LOST_COMMENT_PAGE_SIZE);
    }

    @Override
    public String getApi() {
        return "comment/findCommentsByPage";
    }

    @Override
    public int getHttpMethod() {
        return POST;
    }

    @Override
    public Class getModelClass() {
        return MoreCommentBean.class;
    }
}
