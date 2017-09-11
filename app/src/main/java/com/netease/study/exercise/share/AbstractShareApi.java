package com.netease.study.exercise.share;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by tangjie on 8/24/16.
 */
public abstract class AbstractShareApi implements ShareApi {
    protected Activity mContext;

    public AbstractShareApi(@NonNull Activity context) {
        mContext = context;
    }
}
