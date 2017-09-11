package com.netease.study.exercise.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.netease.hearttouch.htimagepicker.core.HTImageFrom;
import com.netease.hearttouch.htimagepicker.core.HTImagePicker;
import com.netease.hearttouch.htimagepicker.core.HTPickFinishedListener;
import com.netease.hearttouch.htimagepicker.core.HTPickParamsConfig;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.uiconfig.HTRuntimeUIConfig;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.fragment.DialogSimpleFragment;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.module.persondetail.PersonDetailAction;
import com.netease.study.exercise.utils.ExerciseUtils;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.utils.ValidateUtils;
import com.netease.study.exercise.utils.image.ImageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class PersonalDetailActivity extends BaseActivity implements View.OnClickListener, HTPickFinishedListener, DialogSimpleFragment.FDialogOnConfirmClickListener {
    private static final int EDIT = 1;
    private static final int PUBLISH = 2;

    private ImageView mDetailImage;
    private EditText mDetailNickname, mDetailSign, mDetailMobile;
    private DialogSimpleFragment mFragmentDialog;

    private boolean mIsEditing;
    private Boolean mIschange = false;
    private UserBean mUserBean;
    private String mImageurl = "";
    private HashMap<String, String> mParams;
    private Map<String, File> mFiles;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExerciseUtils.closeKeyborad(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetail);
        setTitle(R.string.title_personal_info);
        mUserBean = UserProfile.getUser();
        if (mUserBean == null) {
            UIUtils.showToast(R.string.noPerson);
            finish();
            return;
        }
        initview();
        initdata();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsEditing) {
            MenuItemCompat.setShowAsAction(menu.add(1, PUBLISH, 1, R.string.action_save), MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            MenuItemCompat.setShowAsAction(menu.add(1, EDIT, 1, R.string.action_edit), MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT:
                setEditTextEditable(mDetailSign, true);
                setEditTextEditable(mDetailMobile, true);
                setEditTextEditable(mDetailNickname, true);
                mDetailNickname.setSelection(mDetailNickname.getText().toString().length());
                mDetailImage.setEnabled(true);
                mIsEditing = true;
                invalidateOptionsMenu();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ExerciseUtils.showKeyborad(PersonalDetailActivity.this);
                    }
                }, 300);
                break;
            case PUBLISH:
                if (checkIsChange(true)) {
                    updateDetail();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initview() {
        mDetailImage = (ImageView) findViewById(R.id.personal_detail_image);
        mDetailNickname = (EditText) findViewById(R.id.personal_detail_nickname);
        mDetailSign = (EditText) findViewById(R.id.personal_detail_sign);
        mDetailMobile = (EditText) findViewById(R.id.personal_detail_mobile);

        mDetailImage.setEnabled(false);
        mDetailImage.setOnClickListener(this);
        setEditTextEditable(mDetailNickname, false);
        setEditTextEditable(mDetailSign, false);
        setEditTextEditable(mDetailMobile, false);
    }

    private void initdata() {
        mDetailSign.setText(mUserBean.getSign());
        mDetailMobile.setText(mUserBean.getContact());
        mDetailNickname.setText(mUserBean.getUserName());
        ImageUtils.setUrl(mDetailImage, mUserBean.getPhotoName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_detail_image:
                ExerciseUtils.closeKeyborad(PersonalDetailActivity.this);
                HTPickParamsConfig.HTPickParamsConfigBuilder paramBuilder = HTPickParamsConfig.newBuilder();
                HTRuntimeUIConfig.RuntimeUIConfigBuilder uiBuilder = HTRuntimeUIConfig.newBuilder();
                HTPickParamsConfig paramConfig = paramBuilder.setFrom(HTImageFrom.FROM_LOCAL)
                        .setSelectLimit(1)
                        .setCut(true)
                        .setCutRatio(1.0f)
                        .setTitle("")
                        .build();
                HTImagePicker.INSTANCE.start(this, paramConfig, null, this);
            default:
                break;
        }
    }

    public void updateDetail() {
        if (mIschange) {
            PersonDetailAction.modify(mParams, mImageurl, new PersonDetailAction.IModifyCB() {
                @Override
                public void onModifySuccess(UserBean userBean) {
                    UIUtils.showToast(R.string.save_success);
                    UserProfile.getUser().setUserName(userBean.getUserName());
                    UserProfile.getUser().setContact(userBean.getContact());
                    UserProfile.getUser().setSign(userBean.getSign());
                    UserProfile.getUser().setPhotoName(userBean.getPhotoName());
                    finish();
                }

                @Override
                public void onModifyFailed(int code, String message) {
                    UIUtils.showToast(R.string.save_fail);
                }
            });
        } else {
            UIUtils.showToast(R.string.personal_notmodify);
        }
    }


    protected boolean checkIsChange(boolean showError) {
        mFiles = new IdentityHashMap<>();
        mParams = new HashMap<>();
        if (mDetailNickname.getText().toString().length() == 0) {
            if (showError) {
                UIUtils.showToast(R.string.please_input_nickname);
            }
            return false;
        }
        if (mDetailMobile.getText().toString().length() == 0) {
            if (showError) {
                UIUtils.showToast(R.string.please_input_contact);
            }
            return false;
        }
        if (!ValidateUtils.validateMobileNum(mDetailMobile.getText())) {
            if (showError) {
                UIUtils.showToast(getString(R.string.validate_error_phone));
            }
            return false;
        }
        if (!mUserBean.getUserName().equals(mDetailNickname.getText().toString())) {
            mIschange = true;
            mParams.put("userName", mDetailNickname.getText().toString());
        }
        if (!mUserBean.getSign().equals(mDetailSign.getText().toString())) {
            mIschange = true;
            mParams.put("sign", mDetailSign.getText().toString());
        }
        if (!mUserBean.getContact().equals(mDetailMobile.getText().toString())) {
            mIschange = true;
            mParams.put("contact", mDetailMobile.getText().toString());
        }
        if (!TextUtils.isEmpty(mImageurl)) {
            mFiles.put(new String("file"), new File(mImageurl));
            mIschange = true;
        }
        return true;
    }

    private void setEditTextEditable(EditText editText, boolean value) {
        if (value) {
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        } else {
            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onNavigationBackButtonPressed();
    }

    @Override
    protected void onNavigationBackButtonPressed() {
        if (mIsEditing) {
            checkIsChange(false);
            if (mIschange) {
                showDialog();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    protected void showDialog() {
        mFragmentDialog = new DialogSimpleFragment(null, getResources().getString(R.string.are_you_sure_finish), getResources().getString(R.string.confirm));
        mFragmentDialog.setOnConfirmClickListener(PersonalDetailActivity.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        mFragmentDialog.show(ft, "settingActivity");
    }

    @Override
    public void onImagePickFinished(@Nullable String imageFolderName, List<Image> images) {
        mImageurl = images.get(0).getAbsolutePath();
        ImageUtils.setUrl(mDetailImage, mImageurl);
    }

    @Override
    public void onImagePickCanceled() {

    }

    @Override
    public void onConfirmClick(View view) {
        finish();
    }

    public static void lanuch(Context context) {
        Intent i = new Intent(context, PersonalDetailActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }
}
