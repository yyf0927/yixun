package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.UserProfile;

public class UserPassActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvMobile;

    public static void lanuch(Context context) {
        Intent intent = new Intent(context, UserPassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_pass_setting);

        setTitle(R.string.title_user_pass);

        initView();
    }

    private void initView() {
        mTvMobile = (TextView) findViewById(R.id.user_pass_phone_num_tv);
        findViewById(R.id.activity_user_pass_setting_rt).setOnClickListener(this);

        showUserMobile();
    }

    private void showUserMobile() {
        UserBean user = UserProfile.getUser();
        if (user != null) {
            String mobile = user.getMobile();
            if (!TextUtils.isEmpty(mobile)) {
                mTvMobile.setText(mobile);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_pass_setting_rt: {
                navToResetPass();
                break;
            }
        }
    }

    private void navToResetPass() {
        ResetPasswordActivity.lanuch(this);
    }
}