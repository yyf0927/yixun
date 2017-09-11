package com.netease.study.exercise.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.ExerciseUtils;
import com.netease.study.exercise.utils.UIUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by tangjie on 8/24/16.
 * 微信分享
 */
public class WechatShareApi extends AbstractShareApi {
    public WechatShareApi(@NonNull Activity context) {
        super(context);
    }

    @Override
    public void share(@Nullable String content, @Nullable String imagePath) {
        if (!ExerciseUtils.isWeixinInstalled(mContext)) {
            UIUtils.showToast(R.string.please_install_wechat);
            return;
        }
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, ExerciseConst.WECHAT_APP_ID);
        api.registerApp(ExerciseConst.WECHAT_APP_ID);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        if (!TextUtils.isEmpty(content)) {
            WXTextObject textObject = new WXTextObject();
            textObject.text = content;
            mediaMessage.description = content;
        }
        if (!TextUtils.isEmpty(imagePath)) {
            WXImageObject imageObject = new WXImageObject();
            imageObject.imagePath = imagePath;
            mediaMessage.mediaObject = imageObject;
            Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath), 150, 150);
            mediaMessage.setThumbImage(bmp);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        api.sendReq(req);
    }
}
