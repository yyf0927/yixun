package com.netease.study.exercise.activity.imagepick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.netease.hearttouch.htimagepicker.core.imagepreview.activity.HTBaseImagePreviewActivity;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.util.ContextUtil;
import com.netease.hearttouch.htimagepicker.core.view.photoview.PhotoViewPager;
import com.netease.hearttouch.htimagepicker.defaultui.imagepreview.adapter.ImagePreviewPagerAdapter;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.ColorUtils;

import java.util.Locale;

/**
 * Created by zyl06 on 11/13/16.
 */
public class MultiImagesPreviewActivity extends HTBaseImagePreviewActivity {

    private CheckBox mPickBox;
    private PhotoViewPager mImagePager;

    private Integer mPagePosition = 0;
    private boolean mIsPickBoxChangedByHand = true;
    private MenuItem mFinishItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarColor(ColorUtils.getColor700from500(getResources().getColor(R.color.colorThemePrimary)));
        initContentView();
        initToolbar();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updateFinishButton();
        updateTitle(mPagePosition);
        updatePickMarkBox();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_image, menu);
        mFinishItem = menu.findItem(R.id.action_finish);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_finish) {
            finish(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.gh_text_color));
            updateTitle(mPagePosition);
        }
    }

    private void initContentView() {
        setContentView(R.layout.content_multi_images_preview);

        mPickBox = (CheckBox) findViewById(R.id.cb_preview_image_select);
        mPickBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mIsPickBoxChangedByHand) {
                    mImages.get(mPagePosition).setSelected(isChecked);
                    updateFinishButton();
                }
            }
        });

        mImagePager = (PhotoViewPager) findViewById(R.id.image_fullscreen_pager);
        ImagePreviewPagerAdapter adapter = new ImagePreviewPagerAdapter(this, getImagePaths());
        adapter.setBackgroundColor(getResources().getColor(R.color.white));
        mImagePager.setAdapter(adapter);
        mImagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPagePosition = position;
                updatePickMarkBox();
                updateTitle(position);
                updateFinishButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    protected void setSelectedImageSize(int selectedImageSize, int maxImageSize) {
        if (mFinishItem != null) {
            if (maxImageSize == -1) {
                mFinishItem.setTitle(ContextUtil.INSTANCE.getString(R.string.complete));
            } else {
                mFinishItem.setEnabled(selectedImageSize > 0);
                mFinishItem.setTitle(ContextUtil.INSTANCE.stringFormat(R.string.pick_image_finish_with_number,
                        selectedImageSize, maxImageSize));
            }
        }
    }

    protected void updateFinishButton() {
        int count = 0;
        for (Image img : mImages) {
            if (img.isSelected()) {
                count ++;
            }
        }
        setSelectedImageSize(count, mImages.size());
    }

    protected void updateTitle(int position) {
        String title = String.format(Locale.getDefault(), "%d/%d", position + 1, mImages.size());
        getToolbar().setTitle(title);
    }

    protected void updatePickMarkBox() {
        mIsPickBoxChangedByHand = false;
        mPickBox.setChecked(mImages.get(mPagePosition).isSelected());
        mIsPickBoxChangedByHand = true;
    }
}
