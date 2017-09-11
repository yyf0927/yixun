package com.netease.study.exercise.share;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;

import com.netease.study.exercise.R;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.fragment.InfoLostListFragment;
import com.netease.study.exercise.module.share.ShareTargetClickListener;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.utils.image.ImageLoadListener;
import com.netease.study.exercise.utils.image.ImageUtils;

import java.io.File;

/**
 * Created by tangjie on 8/24/16.
 *
 */
public final class ShareManager {
    private ShareManager() {}

    private static class InstanceHolder {
        final static ShareManager INSTANCE = new ShareManager();
    }

    public static ShareManager getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * 分享内容到系统或者社交app,如果content和imagePath同时为空则不做任何处理.
     * 对于微信分享,不支持图文模式,如果图文并存会丢弃文字.
     * @param type 分享的类型,参考{@link ShareApi.ShareType}
     * @param context 分享需要用到的context
     * @param content 分享的文本信息
     * @param imagePath 分享的图片路径,目前实现中只支持本地文件
     */
    public void share(ShareApi.ShareType type, Activity context, String content, String imagePath) {
        if (TextUtils.isEmpty(content) && TextUtils.isEmpty(imagePath)) {
            return;
        }
        ShareApi api = ShareApiFactory.createShareApi(type, context);
        api.share(content, imagePath);
    }

    public static void share(final Activity activity, final LostListItemBean bean) {
        Dialog dialog = UIUtils.createShareDialog(activity, new ShareTargetClickListener() {
            @Override
            public void onClick(final ShareApi.ShareType type) {
                String photo = bean.getFirstPhoto();
                if (!TextUtils.isEmpty(photo)) {
                    ImageUtils.prefetch(activity, photo, 0, 0, new ImageLoadListener() {
                        @Override
                        public void onLoadSuccess(File resource) {
                            ShareManager.getInstance().share(type, activity, buildShareMessage(bean), resource.getAbsolutePath());
                        }

                        @Override
                        public void onLoadFailed() {
                            Log.w(InfoLostListFragment.class.getSimpleName(), "load image for share failed");
                        }

                        @Override
                        public void onLoadStart() {

                        }
                    });
                } else {
                    ShareManager.getInstance().share(type, activity, buildShareMessage(bean), null);
                }
            }
        });
        dialog.show();
    }

    private static String buildShareMessage(LostListItemBean bean) {
        String message = "%s %s %s\n%s %s";
        return String.format(message, bean.getLostName(), bean.getLostSex(), bean.getDescribes(), bean.getLostPlace(), bean.getLostTime());
    }
}
