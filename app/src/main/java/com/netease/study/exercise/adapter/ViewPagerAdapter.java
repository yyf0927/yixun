package com.netease.study.exercise.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by wangyi on 15/8/5.
 * wangyi hzwangyi@15corp.netease.com
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mListViews;

    public ViewPagerAdapter(List<View> listViews) {
        this.mListViews = listViews;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mListViews.get(arg1));
    }


    @Override
    public int getCount() {
        return mListViews.size();
    }


    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }
}
