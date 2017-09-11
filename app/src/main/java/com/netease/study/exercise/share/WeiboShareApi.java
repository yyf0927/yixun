package com.netease.study.exercise.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.ExerciseUtils;
import com.netease.study.exercise.utils.UIUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

/**
 * Created by tangjie on 8/24/16.
 * 微博分享
 */
public class WeiboShareApi extends AbstractShareApi {

    public WeiboShareApi(@NonNull Activity context) {
        super(context);
    }

    @Override
    public void share(@Nullable String content, @Nullable String imagePath) {
        if (!ExerciseUtils.isWeiboInstalled(mContext)) {
            UIUtils.showToast(R.string.please_install_weibo);
            return;
        }
        IWeiboShareAPI weiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, ExerciseConst.SINA_APP_ID);

        weiboShareAPI.registerApp();
        WeiboMultiMessage message = new WeiboMultiMessage();
        if (!TextUtils.isEmpty(content)) {
            TextObject textObject = new TextObject();
            textObject.text = content;
            message.textObject = textObject;
        }
        if (!TextUtils.isEmpty(imagePath)) {
            ImageObject imageObject = new ImageObject();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageObject.setImageObject(bitmap);
            message.imageObject = imageObject;
        }

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = message;
        weiboShareAPI.sendRequest(mContext, request);
    }
}
