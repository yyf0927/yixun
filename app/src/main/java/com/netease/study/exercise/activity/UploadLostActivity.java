package com.netease.study.exercise.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.netease.hearttouch.htimagepicker.core.HTImageFrom;
import com.netease.hearttouch.htimagepicker.core.HTImagePicker;
import com.netease.hearttouch.htimagepicker.core.HTPickFinishedListener;
import com.netease.hearttouch.htimagepicker.core.HTPickParamsConfig;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.uiconfig.HTRuntimeUIConfig;
import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.AddressBean;
import com.netease.study.exercise.bean.ImageBean;
import com.netease.study.exercise.fragment.UploadLostFragment;
import com.netease.study.exercise.utils.BitmapUtils;
import com.netease.study.exercise.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by netease on 16/11/13.
 */

public class UploadLostActivity extends BaseActivity implements HTPickFinishedListener {
    private static final String UPLOAD_LOST_TAG = "uploadLostTag";

    private List<Image> mImages;
    private Map<String, String> mImagesCacheMap = new HashMap<>();
    private UploadLostFragment mUploadLostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadlost);

        mUploadLostFragment = (UploadLostFragment) getSupportFragmentManager().findFragmentByTag(UPLOAD_LOST_TAG);
        if (mUploadLostFragment == null) {
            mUploadLostFragment = (UploadLostFragment) UploadLostFragment.instantiate(this, UploadLostFragment.class.getName());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mUploadLostFragment, UPLOAD_LOST_TAG)
                    .commit();
        }

        pickImage();
    }

    public void pickImage() {
        HTPickParamsConfig.HTPickParamsConfigBuilder paramBuilder = HTPickParamsConfig.newBuilder();
        HTRuntimeUIConfig.RuntimeUIConfigBuilder uiBuilder = HTRuntimeUIConfig.newBuilder();
        HTPickParamsConfig paramConfig = paramBuilder.setFrom(HTImageFrom.FROM_LOCAL)
                .setImages(mImages)
                .setSelectLimit(6)
                .setCut(false)
                .setCutRatio(1.0f)
                .setTitle("")
                .build();
        HTImagePicker.INSTANCE.start(this, paramConfig, null, this);
    }

    public void onMoreBtnClicked() {
        pickImage();
    }

    public Set<String> getSelectedImages() {
        return mImagesCacheMap.keySet();
    }

    public void removeSelectedImage(String path) {
        Iterator<Image> it = mImages.iterator();
        String removedPath = mImagesCacheMap.remove(path);
        while (it.hasNext()) {
            if (it.next().getAbsolutePath().equals(removedPath)) {
                it.remove();
                break;
            }
        }
    }

    @Override
    public void onImagePickFinished(@Nullable String imageFolderName, List<Image> images) {
        mImages = images;
        new DealImageScaleTask().execute();
    }

    @Override
    public void onImagePickCanceled() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
//            case CBPickerActivity.REQUEST_CODE_PICKIAMGE:
//                if (resultCode == Activity.RESULT_OK)
//                {
//                    //mSelectedImage.clear();
//                    Set<Image> images = CBPickerActivity.intent2Set(data);
//                    if ( images != null )
//                    {
//                        mSelectedImage.clear();
//                        mSelectedImage.addAll(images);
//                        mUploadHelpFragment.syncGallery();
//                    }
//                }
//                break;
            case PoiKeywordSearchActivity.REQUEST_CODE_POI:
                if (resultCode == Activity.RESULT_OK) {
                    AddressBean bean = PoiKeywordSearchActivity.intent2Address(data);
                    mUploadLostFragment.syncLocation(bean);
                }
                break;
        }

    }

    private class DealImageScaleTask extends AsyncTask<Void, Void, Void> {
        private HashSet<ImageBean> mSelectImages = new HashSet<>();
        private ProgressDialog mPd;

        @Override
        protected Void doInBackground(Void... params) {
            for (Image image : mImages) {
                Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(image.getAbsolutePath(), 600, 600);
                if (bitmap != null) {
                    FileOutputStream os = null;
                    try {
                        String cacheName = image.getAbsolutePath().hashCode() + "";
                        String scalePath = ExerciseConst.WORK_PATH_CACHE_IMAGE + File.separator + cacheName;
                        os = new FileOutputStream(scalePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                        mSelectImages.add(new ImageBean(Uri.parse(scalePath)));
                        mImagesCacheMap.put(scalePath, image.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        IoUtils.closeSilently(os);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            mPd = new ProgressDialog(UploadLostActivity.this);
            mPd.setMessage(getResources().getString(R.string.dealingImage));
            mPd.setCancelable(false);
            mPd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!isFinishing() && mPd.isShowing()) {
                mPd.dismiss();
            }
            mUploadLostFragment.syncGallery(mSelectImages);
        }
    }

    public static void lanuch(Context context) {
        Intent intent = new Intent(context, UploadLostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }
}
