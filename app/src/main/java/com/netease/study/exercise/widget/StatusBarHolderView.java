package com.netease.study.exercise.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.netease.study.exercise.utils.ExerciseUtils;


public class StatusBarHolderView extends View {
    public StatusBarHolderView(Context context) {
        this(context, null);
    }

    public StatusBarHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(ExerciseUtils.getStatusBarHeight(getContext()), MeasureSpec.getMode(heightMeasureSpec)));
    }
}
