package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.SearchFilterBean;
import com.netease.study.exercise.fragment.SearchFilterResultFragment;

/**
 * Created by liuqijun on 12/4/16.
 */

public class SearchResultActivity extends BaseActivity {
    public final static String EXTRA = "extra";

    public static void launch(Context context, SearchFilterBean searchFilterBean) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA, JSON.toJSONString(searchFilterBean));
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setTitle(R.string.search_title);

        Fragment fragment = Fragment.instantiate(this, SearchFilterResultFragment.class.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment, null)
                .commit();

    }

}
