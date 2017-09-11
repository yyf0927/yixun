package com.netease.study.exercise.widget;

/**
 * Created by netease on 15/7/24.
 * Beifeng bwifeng@163.com
 *
 * 平滑的效果
 */

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class ParallaxPageTransformer implements PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        page.setAlpha(1);
        page.setTranslationX(0);
        page.setTranslationY(0);
        page.setPivotX(page.getWidth() / 2);
        page.setPivotY(page.getHeight() / 2);
        page.setScaleX(1);
        page.setScaleY(1);
        page.setRotation(0);

    }
}
