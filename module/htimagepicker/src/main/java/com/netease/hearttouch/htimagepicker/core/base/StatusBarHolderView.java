package com.netease.hearttouch.htimagepicker.core.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyl06 on 11/12/16.
 */
public class StatusBarHolderView extends View {

    public StatusBarHolderView(Context context) {
        this(context, null);
    }

    public StatusBarHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getStatusBarHeight(getContext()), View.MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
