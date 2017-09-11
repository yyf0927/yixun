package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {
    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutUsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        setTitle(R.string.about_us);
    }
}
