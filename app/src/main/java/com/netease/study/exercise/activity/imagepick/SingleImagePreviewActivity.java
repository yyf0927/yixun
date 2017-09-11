package com.netease.study.exercise.activity.imagepick;

/**
 * Created by zyl06 on 11/13/16.
 */
public class SingleImagePreviewActivity extends MultiImagesPreviewActivity {

    @Override
    protected void updateFinishButton() {
        setSelectedImageSize(0, -1);
    }

    @Override
    protected void updateTitle(int position) {
        getToolbar().setTitle("");
    }
}
