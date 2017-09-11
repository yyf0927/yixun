package com.netease.study.exercise.activity.imagepick;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.hearttouch.htimagepicker.core.HTImageFrom;
import com.netease.hearttouch.htimagepicker.core.imagepick.activity.HTBaseImagePickActivity;
import com.netease.hearttouch.htimagepicker.core.imagepick.event.EventNotifier;
import com.netease.hearttouch.htimagepicker.core.imagepreview.listener.IIntentProcess;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageFolder;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageHelper;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageInfoUtil;
import com.netease.hearttouch.htimagepicker.core.imagescan.Thumbnail;
import com.netease.hearttouch.htimagepicker.core.util.ContextUtil;
import com.netease.hearttouch.htimagepicker.core.view.PopupwindowMenu;
import com.netease.hearttouch.htimagepicker.defaultui.imagepick.adapter.ImageFolderListAdapter;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 9/14/16.
 */

public class ImagePickActivity extends HTBaseImagePickActivity
        implements AdapterView.OnItemClickListener,
        View.OnClickListener,
        SelectImageChangeListener {

    private static final String TAG = ImagePickActivity.class.getSimpleName();
    private PopupwindowMenu mPopupwindowMenu;
    private int mPopupWindowHeight = 0;

    private GridView mImageGridView;
    private ImageGridAdapter mImageGridAdapter;
    private TextView mPreviewBtn;
    private TextView mFolderNameView;
    private ImageFolder mAllImageFolder;
    private boolean mIsConfirmUse = false;
    private MenuItem mFinishItem;

    public ImagePickActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.content_image_pick);

        setStatusBarColor(ColorUtils.getColor700from500(getResources().getColor(R.color.colorThemePrimary)));
        initToolbar();
        initContentView();
        EventNotifier.registerUpdatePickMarksListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_image, menu);

        mFinishItem = menu.findItem(R.id.action_finish);
        setSelectedImageSize(0, getSelectLimit());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_finish) {
            if (!getSelectedImages().isEmpty()) {
                this.mIsConfirmUse = true;
                this.completeSelection(getSelectedImages(), this.isCut());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            updateButtons();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.gh_text_color));
        }
    }

    private void setSelectedImageSize(int selectedImageSize, int maxImageSize) {
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

    @Override
    protected void onNavigationBackButtonPressed() {
        mIsConfirmUse = false;
        finish();
    }

    private void initContentView() {
        mImageGridView = (GridView) findViewById(R.id.pick_image_grid_view);
        mImageGridView.setOnItemClickListener(this);
        mPreviewBtn = (TextView) findViewById(R.id.preview_button);
        mPreviewBtn.setOnClickListener(this);
        View showImgFolder = findViewById(R.id.show_all_image_folders);
        mFolderNameView = (TextView) findViewById(R.id.show_all_image_folders_text);
        showImgFolder.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventNotifier.unregisterUpdatePickMarksListener();
    }

    @Override
    public boolean isConfirmUse() {
        return this.mIsConfirmUse;
    }

    /**
     * 从预览界面更新数据的回调。已经处理了选中数量超出的情况
     * @param images 新的选中的图片列表
     */
    @Override
    public void onUpdateSelectedImagesFromPreview(List<Image> images) {
        this.mImageGridAdapter.notifyDataSetChanged();
        updateButtons();
    }

    @Override
    public void onUpdateAllImageFolders(List<Thumbnail> thumbnails, List<ImageFolder> imageFolders) {
        if (imageFolders != null && imageFolders.size() > 0) {
            this.mAllImageFolder = imageFolders.get(0);
            this.mAllImageFolder.setName(this.getString(R.string.all_image_folders));
            this.mImageGridAdapter = new ImageGridAdapter(this, this.mCurrImageFolder, this);
            this.mImageGridView.setAdapter(this.mImageGridAdapter);
            this.mFolderNameView.setText(this.mCurrImageFolder.getName());
        }

        updateButtons();
    }

    @Override
    public void onImageSelectedOverflow(int selectedSize, int selectedLimit) {
        String msg = getResources().getString(R.string.pick_image_max_pick_warning_with_number, selectedLimit);
        Toast.makeText(ImagePickActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelFromCamera() {
        if (getImageFrom() == HTImageFrom.FROM_CAMERA) {
            this.mIsConfirmUse = false;
            this.finish();
        }
    }

    @Override
    public void onPickedFromCamera(Image image) {
        if (getSelectedImages().size() < this.getSelectLimit()) {
            image.setSelected(true);
            getSelectedImages().add(image);
            this.mImageGridAdapter.notifyDataSetChanged();
        }

        if (this.getImageFrom() == HTImageFrom.FROM_CAMERA) {
            this.mIsConfirmUse = true;
            this.finish();
        }
    }

    @Override
    public void onCompleteFromCut(HTImageFrom from) {
        this.mIsConfirmUse = true;
    }

    @Override
    public void onCancelFromCut(HTImageFrom from) {
        this.mIsConfirmUse = false;
        if (from == HTImageFrom.FROM_LOCAL) {
            ImageHelper.setSelectFlag(getSelectedImages(), false);
            getSelectedImages().clear();
            mImageGridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            this.startTakePhoto();
            return;
        }

        int imagePosition = position - 1;
        Image image = this.mCurrImageFolder.getImages().get(imagePosition);
        startPreviewSingleImage(image, (IIntentProcess) null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mIsConfirmUse = false;
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_all_image_folders:
                popupFolderSelectDialog();
                break;
            case R.id.preview_button:
                this.startPreviewMultiImages(getSelectedImages(), (IIntentProcess) null);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onImageSelectionChange(Image image) {
        if (image != null) {
            List<Image> from = new ArrayList<>();
            from.add(image);
            boolean success = updateSelection(from, false);
            updateButtons();
            return success;
        }

        return false;
    }

    private void popupFolderSelectDialog() {
        final int maxFolderItemCount = 3;

        if (this.mPopupwindowMenu == null) {
            final List<ImageFolder> imageFolders = ImageInfoUtil.getImageFolders();
            int count = (imageFolders != null && imageFolders.size() > maxFolderItemCount) ? maxFolderItemCount : imageFolders.size();
            mPopupWindowHeight = count * (int) ContextUtil.INSTANCE.getDimen(R.dimen.pick_image_folder_list_item_height);

            mPopupwindowMenu = new PopupwindowMenu(ImagePickActivity.this, ViewGroup.LayoutParams.MATCH_PARENT, mPopupWindowHeight, Gravity.BOTTOM);
            ImageFolderListAdapter adapter = new ImageFolderListAdapter(this);
            mPopupwindowMenu.setCustomAdapter(adapter);
            mPopupwindowMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (ImagePickActivity.this.mImageGridAdapter != null && imageFolders != null && imageFolders.size() > 0) {
                            ImagePickActivity.this.mCurrImageFolder = imageFolders.get(position);
                            ImagePickActivity.this.getSelectedImages().clear();
                            ImagePickActivity.this.mImageGridAdapter.changeImageFolder(ImagePickActivity.this.mCurrImageFolder);
                            ImagePickActivity.this.mFolderNameView.setText(ImagePickActivity.this.mCurrImageFolder.getName());
                            ImagePickActivity.this.updateButtons();
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        Log.e("HTImagePickActivity", var10.toString());
                    } finally {
                        ImagePickActivity.this.mPopupwindowMenu.dismiss();
                    }
                }
            });
            mPopupwindowMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    // do nothing
                }
            });
        }

        View buttonContainer = findViewById(R.id.bottom_container);
        mPopupwindowMenu.showAtLocation(buttonContainer, Gravity.NO_GRAVITY, 0, 0, 0, buttonContainer.getHeight());
    }

    private void updateButtons() {
        List<Image> selectedImages = getSelectedImages();
        setSelectedImageSize(selectedImages.size(), getSelectLimit());
        mPreviewBtn.setText(getResources().getString(R.string.preview_format, getSelectedImages().size()));
        mPreviewBtn.setEnabled(!selectedImages.isEmpty());
        mPreviewBtn.setClickable(!selectedImages.isEmpty());
    }

    private void completeSelection(List<Image> images, boolean needCut) {
        if (images.size() > 0 && needCut) {
            Image image = images.get(0);
            startCutImage(image.getAbsolutePath(), HTImageFrom.FROM_LOCAL.toString(), (IIntentProcess) null);
        } else {
            finish();
        }
    }
}