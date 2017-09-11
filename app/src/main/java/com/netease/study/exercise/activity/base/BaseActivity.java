package com.netease.study.exercise.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;

import com.netease.study.exercise.R;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.utils.ColorUtils;
import com.netease.study.exercise.widget.StatusBarHolderView;

import java.util.ArrayList;

/**
 * Created by zyl06 on 9/16/16.
 */

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    protected StatusBarHolderView mStatusBarView;

    protected ArrayList<BaseRequest> mRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequests = new ArrayList<>();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        LayoutInflater.from(this).inflate(layoutResID, (ViewGroup) findViewById(R.id.container));
        initToolbarLayout();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(R.layout.activity_base);
        ((ViewGroup) findViewById(R.id.container)).addView(view);
        initToolbarLayout();
    }

    private void initToolbarLayout() {
        initToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarView = (StatusBarHolderView) ((ViewStub) findViewById(R.id.statusbar)).inflate();
            transparentStatusBar(true);
        }
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.gh_text_color));
        setSupportActionBar(mToolbar);
        setDisplayHomeAsUpEnabled();
    }

    public void transparentStatusBar(boolean enable) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (enable) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                mStatusBarView.setBackgroundColor(getResources().getColor(R.color.colorThemePrimary));
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(ColorUtils.getColor700from500(getResources().getColor(R.color.colorThemePrimary)));
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    public void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()) {
            actionBar.hide();
            transparentStatusBar(false);
            if (mStatusBarView != null) {
                mStatusBarView.setVisibility(View.GONE);
            }
        }
    }

    public void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !actionBar.isShowing()) {
            actionBar.show();
            transparentStatusBar(true);
            if (mStatusBarView != null) {
                mStatusBarView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onNavigationBackButtonPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setDisplayHomeAsUpEnabled() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onNavigationBackButtonPressed() {
        finish();
    }


    public void addRequest(BaseRequest request){
        mRequests.add(request);
    }

    public void removeRequest(BaseRequest request){
        mRequests.remove(request);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(BaseRequest request : mRequests){
            request.cancel();
            request= null;
        }
    }

}
