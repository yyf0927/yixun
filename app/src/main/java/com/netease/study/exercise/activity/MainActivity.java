package com.netease.study.exercise.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.fragment.LostListFragment;
import com.netease.study.exercise.fragment.MeFragment;
import com.netease.study.exercise.fragment.SettingsFragment;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.utils.UIUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private PagerSlidingTabStrip mPagerTab;
    private ViewPager mPager;

    private long mLastBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        this.findViewById(R.id.fab_post).setOnClickListener(this);

        mPagerTab = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);
        // 均分
        mPagerTab.setShouldExpand(true);
        mPagerTab.setIndicatorHeight(getResources().getDimensionPixelSize(R.dimen.tab_indicator_height));
        mPagerTab.setTabBackground(0);
        mPagerTab.setDividerColor(Color.TRANSPARENT);

        mPager = (ViewPager) findViewById(R.id.main_pages);
        // 3个页，不需要重建
        mPager.setOffscreenPageLimit(2);

        setupPager();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastBackPressedTime >= 2000) {
            mLastBackPressedTime = System.currentTimeMillis();
            UIUtils.showToast(R.string.pressagain_exit);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchActivity.launch(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setDisplayHomeAsUpEnabled() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab_post:
                checkIsLogin();
                break;
        }
    }

    private void checkIsLogin() {
        //判定当前是否已经登录

        if (UserProfile.isLogin()) {
            post();
        } else {
            EntranceActivity.launch(this, LoginAction.LOGIN_CODE);
        }
    }

    private void post() {
        UploadLostActivity.lanuch(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LoginAction.LOGIN_CODE) {
            //登录成功返回
            post();
        }
    }

    private void setupPager() {
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new LostListFragment();
                } else if (position == 1) {
                    return new MeFragment();
                } else if (position == 2) {
                    return new SettingsFragment();
                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return getString(R.string.tab_lost_list);
                } else if (position == 1) {
                    return getString(R.string.tab_me);
                } else if (position == 2) {
                    return getString(R.string.tab_settings);
                }

                return super.getPageTitle(position);
            }
        });
        mPagerTab.setViewPager(mPager);
    }
}
