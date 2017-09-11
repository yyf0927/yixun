package com.netease.study.exercise.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.bean.ImageBean;
import com.netease.study.exercise.utils.image.ImageUtils;

public class GalleryWidget extends GridLayout implements View.OnClickListener {
    private IOnClickListener mListener;

    public GalleryWidget(Context context) {
        super(context);
        init();
    }

    public GalleryWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ImageView insertImage(final ImageBean image) {
        LayoutParams params = layoutParams();

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View root = layoutInflater.inflate(R.layout.plugin_gallery_closable, this, false);
        ImageView imageView = (ImageView) root.findViewById(R.id.thumbnail_image);
        if (image != null) {
            View v = root.findViewById(R.id.thumbnail_close);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onSelectedImageClosed(image);
                        GalleryWidget.this.removeView(root);
                    }
                }
            });
        }
        ImageUtils.setUrl(imageView, image.getUri().toString());
        this.addView(root, 0, params);
        return imageView;
    }


    private void insertButton() {
        LayoutParams params = layoutParams();
        Button button = new Button(getContext());
        button.setBackgroundResource(R.drawable.icon_more_plus);
        button.setLayoutParams(params);
        this.addView(button, 0, params);

        button.setOnClickListener(this);
    }

    private static final int MARGIN = 12;
    private static final int COLUMN = 4;
    private int mMeausuredItemWidth;


    private LayoutParams layoutParams() {
        LayoutParams mLayoutParams = new LayoutParams();
        mLayoutParams.width = mMeausuredItemWidth;
        mLayoutParams.height = mMeausuredItemWidth;
        mLayoutParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        return mLayoutParams;
    }

    private void init() {
        int screenWidth = ExerciseConst.SCREEN_WIDTH;
        int width = screenWidth - MARGIN * 2 * (COLUMN + 1);
        mMeausuredItemWidth = width / COLUMN;
        this.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);
        insertButton();
    }

    public void clear() {
        this.removeAllViews();
        insertButton();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) mListener.onMoreBtnClicked();
    }

    public interface IOnClickListener {
        void onMoreBtnClicked();

        void onSelectedImageClosed(ImageBean image);
    }

    public IOnClickListener getListener() {
        return mListener;
    }

    public void setListener(IOnClickListener mListener) {
        this.mListener = mListener;
    }
}
