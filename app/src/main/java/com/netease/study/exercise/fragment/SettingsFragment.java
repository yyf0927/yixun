package com.netease.study.exercise.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.AboutUsActivity;
import com.netease.study.exercise.activity.EntranceActivity;
import com.netease.study.exercise.activity.PersonalDetailActivity;
import com.netease.study.exercise.activity.UserPassActivity;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.utils.AsyncUtils;
import com.netease.study.exercise.utils.ToastWrapper;
import com.netease.study.exercise.utils.image.ClearDiskCacheListener;
import com.netease.study.exercise.utils.image.ImageUtils;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

import java.io.File;

public class SettingsFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.setting_personal_data_rt).setOnClickListener(this);
        view.findViewById(R.id.setting_user_pass_rt).setOnClickListener(this);
        view.findViewById(R.id.setting_about_us_rt).setOnClickListener(this);
        view.findViewById(R.id.setting_login_exit_lt).setOnClickListener(this);
        view.findViewById(R.id.setting_make_fake_push).setOnClickListener(this);
        view.findViewById(R.id.setting_cache_clear_rt).setOnClickListener(this);

        tvLogin = (TextView) view.findViewById(R.id.setting_login_exit_tip_tv);
    }

    @Override
    public void onResume() {
        super.onResume();

        showUser();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_personal_data_rt: {
                if (!UserProfile.isLogin()) {
                    navToLogin();
                } else {
                    navToPersonalData();
                }
                break;
            }
            case R.id.setting_user_pass_rt: {
                if (!UserProfile.isLogin()) {
                    navToLogin();
                } else {
                    navToUserPass();
                }
                break;
            }
            case R.id.setting_about_us_rt: {
                navToAboutUs();
                break;
            }
            case R.id.setting_login_exit_lt: {
                if (UserProfile.isLogin()) {
                    showLogoutDialog();
                } else {
                    navToLogin();
                }
                break;
            }
            case R.id.setting_make_fake_push: {
                makeFakePush();
                break;
            }
            case R.id.setting_cache_clear_rt: {
                showClearCacheDialog();
                break;
            }
        }
    }

    private void navToLogin() {
        Intent intent = new Intent();
        intent.setClass(getContext(), EntranceActivity.class);
        startActivity(intent);
    }

    private void navToPersonalData() {
        PersonalDetailActivity.lanuch(getContext());
    }

    private void navToUserPass() {
        UserPassActivity.lanuch(getContext());
    }

    private void navToAboutUs() {
        AboutUsActivity.launch(getContext());
    }

    private void showLogoutDialog() {
        DialogSimpleFragment dialog = new DialogSimpleFragment(null, getResources().getString(R.string.logout_confirm), getResources().getString(R.string.confirm));
        dialog.setOnConfirmClickListener(new DialogSimpleFragment.FDialogOnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                logout();
                showUser();
            }
        });
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        dialog.show(ft, "logout");
    }

    private void logout() {
        LoginAction.logout();
    }

    private void showUser() {
        tvLogin.setText(UserProfile.isLogin() ? R.string.settings_exit : R.string.settings_login);
    }

    /**
     * 为了演示推送功能，此处采用小米推送的JavaSDK，发送一个模拟的消息。
     */
    private void makeFakePush() {
        final String regId = AppProfile.getXiaoMiRegId();
        if (!TextUtils.isEmpty(regId)) {
            Constants.useOfficial();
            final Sender sender = new Sender(ExerciseConst.MI_PUSH_SCER);
            String messagePayload = getString(R.string.fake_message_payload);
            String title = getString(R.string.fake_message_title);
            String description = getString(R.string.fake_message_desc);
            final Message message = new Message.Builder()
                    .title(title)
                    .description(description).payload(messagePayload)
                    .restrictedPackageName("com.netease.study.exercise")
                    .notifyType(1)     // 使用默认提示音提示
                    .build();
            AsyncUtils.runOnMultiExecutor(new Runnable() {
                @Override
                public void run() {
                    try {
                        Result result = sender.send(message, regId, 3);
                        Log.v("Server response: ", "MessageId: " + result.getMessageId()
                                + " ErrorCode: " + result.getErrorCode().toString()
                                + " Reason: " + result.getReason());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void showClearCacheDialog() {
        DialogSimpleFragment dialog = new DialogSimpleFragment(null, getResources().getString(R.string.cache_clear_confirm), getResources().getString(R.string.confirm));
        dialog.setOnConfirmClickListener(new DialogSimpleFragment.FDialogOnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                clearCache();
            }
        });
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        dialog.show(ft, "clearCache");
    }

    private void clearCache() {
        ImageUtils.clearDiskCache(getContext(), new ClearDiskCacheListener() {
            @Override
            public void onDiskCacheCleared() {
                File[] files = new File(ExerciseConst.WORK_PATH_CACHE_IMAGE).listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
                getView().post(new Runnable() {
                    @Override
                    public void run() {
                        ToastWrapper.makeShortToast(R.string.cache_clear_done);
                    }
                });
            }
        });
    }
}
