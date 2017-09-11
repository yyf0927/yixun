package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.fragment.ResetPasswordFragment;

public class ResetPasswordActivity extends BaseActivity {
    public static void lanuch(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        setTitle(R.string.login_common_forget);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResetPasswordFragment());
        transaction.commit();
    }
}
