package com.netease.study.exercise.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.activity.MyInfoLostListActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 用于接收小米推送的回调.
 * 所有回调都在非UI线程执行.
 */
public class MiPushMessageReceiver extends PushMessageReceiver {
    private static final String TAG = "MiPushMessageReceiver";

    private String mRegId;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.i(TAG, "message received from mi push:" + message.getContent());
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        MyInfoLostListActivity.launchFromNotification(context);
        MiPushClient.clearNotification(context);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.i(TAG, "message received from mi push:" + message.getContent());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                AppProfile.setXiaoMiRegId(mRegId);
            } else {
                Log.w(TAG, "register mi push failed, code is " + message.getResultCode());
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {

    }

}
