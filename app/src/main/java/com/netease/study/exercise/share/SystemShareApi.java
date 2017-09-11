package com.netease.study.exercise.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netease.study.exercise.R;

import java.io.File;

/**
 * Created by tangjie on 8/24/16.
 * 系统默认方式分享
 */
public class SystemShareApi extends AbstractShareApi {
    public SystemShareApi(@NonNull Activity context) {
        super(context);
    }

    @Override
    public void share(@Nullable String content, @Nullable String imagePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        if (!TextUtils.isEmpty(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setType("text/plain");
        }
        if (!TextUtils.isEmpty(imagePath)) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
            intent.setType("image/*");
        }
        mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.share_title)));
    }
}
