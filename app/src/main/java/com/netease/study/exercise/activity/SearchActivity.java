package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.fragment.SearchFragment;

/**
 * Created by liuqijun on 12/3/16.
 */

public class SearchActivity extends BaseActivity {
    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(R.string.search);

        Fragment fragment = Fragment.instantiate(this, SearchFragment.class.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment, null)
                .commit();

    }


}
