package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;

/**
 * Created by netease on 16/11/27.
 */

public class OtherInfoLostListActivity extends BaseActivity {
    public final static String USERID = "USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolostlist);
        setTitle(R.string.title_other_lostlist);
    }

    public static void launch(Context context, long id) {
        Intent intent = new Intent(context, OtherInfoLostListActivity.class);
        intent.putExtra(USERID, id);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }
}
