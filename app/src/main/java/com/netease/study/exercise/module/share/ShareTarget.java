package com.netease.study.exercise.module.share;

import com.netease.study.exercise.share.ShareApi;

/**
 * Created by tangjie on 16/12/6.
 * 用于描述分享目标的数据。
 */

public class ShareTarget {
    public ShareTarget(String desc, int logoId, ShareApi.ShareType type) {
        mDesc = desc;
        mResId = logoId;
        mType = type;
    }
    String mDesc;
    int mResId;
    ShareApi.ShareType mType;

    public ShareApi.ShareType getType()  {
        return mType;
    }
}
