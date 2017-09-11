package com.netease.study.exercise.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.ListAdapter;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.R;
import com.netease.study.exercise.module.share.ShareListAdapter;
import com.netease.study.exercise.module.share.ShareTarget;
import com.netease.study.exercise.module.share.ShareTargetClickListener;
import com.netease.study.exercise.share.ShareApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netease on 16/11/20.
 */

public class UIUtils {

    private static List<ShareTarget> sAllShareTargets;

    /**
     * 初始化分享目标的数据，非线程安全。
     * @return
     */
    private static List<ShareTarget> getAllTarget() {
        if (null == sAllShareTargets) {
            sAllShareTargets = new ArrayList<>();
            Context context = AppProfile.getContext();
            sAllShareTargets.add(new ShareTarget(context.getString(R.string.weibo),
                    R.drawable.weibo, ShareApi.ShareType.WEIBO));
            sAllShareTargets.add(new ShareTarget(context.getString(R.string.weixin),
                    R.drawable.weixin, ShareApi.ShareType.WECHAT));
        }
        return sAllShareTargets;
    }

    public static void showToast(int resId) {
        showToast(AppProfile.getContext().getString(resId));
    }

    public static void showToast(String msg) {
        ToastWrapper.makeLongToast(msg);
    }

    public static Dialog createShareDialog(Activity activity, final ShareTargetClickListener listener) {
        ListAdapter adapter = new ShareListAdapter(getAllTarget());
        Dialog dialog = createDialogWithList(activity, "", adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which < sAllShareTargets.size()) {
                    ShareTarget target = sAllShareTargets.get(which);
                    listener.onClick(target.getType());
                }
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog createDialogWithList(Activity activity, String title, ListAdapter adapter, DialogInterface.OnClickListener l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setAdapter(adapter, l);
        return builder.create();
    }
}
