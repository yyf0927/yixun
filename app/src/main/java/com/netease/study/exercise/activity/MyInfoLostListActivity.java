package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;

/**
 * Created by netease on 16/11/27.
 */

public class MyInfoLostListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolostlist);
        setTitle(R.string.title_my_lostlist);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MyInfoLostListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    public static void launchFromNotification(Context context) {
        Intent intent = new Intent(context, MyInfoLostListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
