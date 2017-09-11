/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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

import com.netease.hearttouch.htimagepicker.R;

/**
 * Created by zyl06 on 2/19/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    protected StatusBarHolderView mStatusBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.htimagepicker_activity_base);
        LayoutInflater.from(this).inflate(layoutResID, (ViewGroup) findViewById(R.id.container));
        initToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarView = (StatusBarHolderView) ((ViewStub) findViewById(R.id.statusbar)).inflate();
            transparentStatusBar(true);
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setDisplayHomeAsUpEnabled();
    }

    public void transparentStatusBar(boolean enable) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (enable) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            mStatusBarView.setBackgroundColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}