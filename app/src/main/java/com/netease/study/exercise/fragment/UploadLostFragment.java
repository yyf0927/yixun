package com.netease.study.exercise.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.PoiKeywordSearchActivity;
import com.netease.study.exercise.activity.UploadLostActivity;
import com.netease.study.exercise.bean.AddressBean;
import com.netease.study.exercise.bean.ImageBean;
import com.netease.study.exercise.bean.LostPersonBean;
import com.netease.study.exercise.bean.MatchListBean;
import com.netease.study.exercise.module.uploadlost.UploadLostAction;
import com.netease.study.exercise.utils.DateUtils;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.utils.ValidateUtils;
import com.netease.study.exercise.widget.GalleryWidget;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;


public class UploadLostFragment extends BaseFragment implements View.OnClickListener, GalleryWidget.IOnClickListener, AMapLocationListener {
    private static final int PUBLISH = 1;

    private Button mPostBtn;
    private GalleryWidget mGalleryWidget;
    private EditText mUploadEditName;
    private RadioGroup mUploadRaidoSex;
    private EditText mUploadEditHeight;
    private Button mUploadBtnBirthday;
    private Button mUploadBtnLostday;
    private EditText mUploadEditContact;
    private EditText mUploadEditDetail1;
    private EditText mUploadEditDetail2;
    private EditText mUploadEditDetail3;
    private EditText mUploadEditBonus;
    private Button mUploadLocation;

    private InputMethodManager mInputMethodManager;
    private boolean mIsUploading = false;
    private LinkedHashMap<EditText, String> mTips;
    private LostPersonBean mLostPersonBean = new LostPersonBean();
    private AddressBean mAddressBean;
    private LocationManagerProxy mLocationManagerProxy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTips = new LinkedHashMap<>();
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItemCompat.setShowAsAction(menu.add(1, PUBLISH, 1, R.string.action_post), MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PUBLISH:
                mPostBtn.performClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setTitle(getString(R.string.title_description));

        View view = inflater.inflate(R.layout.fragment_uploadlost, null);
        mPostBtn = (Button) view.findViewById(R.id.btn_post);
        mGalleryWidget = (GalleryWidget) view.findViewById(R.id.grid_widget);
        mUploadEditName = (EditText) view.findViewById(R.id.upload_edit_name);
        mUploadRaidoSex = (RadioGroup) view.findViewById(R.id.upload_radio_sex);
        mUploadEditHeight = (EditText) view.findViewById(R.id.upload_edit_height);
        mUploadEditContact = (EditText) view.findViewById(R.id.upload_edit_contact);
        mUploadEditDetail1 = (EditText) view.findViewById(R.id.upload_edit_detail1);
        mUploadEditDetail2 = (EditText) view.findViewById(R.id.upload_edit_detail2);
        mUploadEditDetail3 = (EditText) view.findViewById(R.id.upload_edit_detail3);
        mUploadEditBonus = (EditText) view.findViewById(R.id.upload_edit_bonus);
        mUploadBtnBirthday = (Button) view.findViewById(R.id.upload_btn_birthday);
        mUploadBtnLostday = (Button) view.findViewById(R.id.upload_btn_lostday);
        mUploadLocation = (Button) view.findViewById(R.id.upload_btn_lostlocation);
        mUploadRaidoSex.check(R.id.upload_sex_male);

        mUploadBtnBirthday.setOnClickListener(this);
        mUploadBtnLostday.setOnClickListener(this);
        mUploadLocation.setOnClickListener(this);
        mGalleryWidget.setListener(this);
        mPostBtn.setOnClickListener(this);

        mTips.put(mUploadEditName, getString(R.string.please_inputName));
        mTips.put(mUploadEditHeight, getString(R.string.please_inputTall));
        mTips.put(mUploadEditContact, getString(R.string.please_inputPhone));

        initRequestLocation();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRequestLocation();
    }

    @Override
    public void onClick(View v) {
        mInputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (v == mPostBtn) {
            uploadMultiFile();
        } else if (v == mUploadBtnBirthday || v == mUploadBtnLostday) {
            Calendar now = Calendar.getInstance();
            selectDate(v.getId()
                    , now.get(Calendar.YEAR)
                    , now.get(Calendar.MONTH)
                    , now.get(Calendar.DAY_OF_MONTH));
        } else if (v == mUploadLocation) {
            PoiKeywordSearchActivity.launchActivityForResult(getActivity(), mAddressBean);
        }
    }

    private void selectDate(final int fromId, int year, final int month, int day) {
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                Date current = DateUtils.str2Date(date);

                if (current != null && current.getTime() < System.currentTimeMillis()) {
                    date = DateUtils.dateStr2strShort(date);
                    Button tmp = null;
                    if (fromId == mUploadBtnBirthday.getId()) {
                        tmp = mUploadBtnBirthday;
                        mLostPersonBean.setBirth(date);
                    } else {
                        tmp = mUploadBtnLostday;
                        mLostPersonBean.setLostTime(date);
                    }
                    tmp.setText(date);
                } else {
                    UIUtils.showToast("错误的日期");
                }
            }
        }, year, month, day);
        dpd.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        dpd.show();
    }

    private boolean formCheck() {
        if (mIsUploading) {
            UIUtils.showToast(getString(R.string.tip_uploading));
            return false;
        }
        Set<EditText> sets = mTips.keySet();
        String tmp = null;
        for (EditText edit : sets) {
            tmp = edit.getText().toString();
            if (TextUtils.isEmpty(tmp)) {
                edit.requestFocus();
                UIUtils.showToast(mTips.get(edit));
                return false;
            }
        }

        if (!ValidateUtils.validateMobileNum(mLostPersonBean.getContact())) {
            UIUtils.showToast(getString(R.string.validate_error_phone));
            return false;
        }
        if (!ValidateUtils.validateHeight(mLostPersonBean.getLostHeight())) {
            UIUtils.showToast(getString(R.string.validate_error_height));
            return false;
        }

        if (TextUtils.isEmpty(mLostPersonBean.getLostTime())) {
            UIUtils.showToast(getString(R.string.hint_lostDate));
            return false;
        }


        if (TextUtils.isEmpty(mLostPersonBean.getBirth())) {
            UIUtils.showToast(getString(R.string.tip_pleaseinput_lostbirth));
            return false;
        }
        if (mAddressBean == null) {
            UIUtils.showToast(getString(R.string.tip_pleaseinputlocation));
            return false;
        }

        return true;
    }

    public void uploadMultiFile() {
        mInputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        mLostPersonBean.clear();
        mLostPersonBean.setLostName(mUploadEditName.getText().toString());
        mLostPersonBean.setContact(mUploadEditContact.getText().toString());
        mLostPersonBean.setLostSex(mUploadRaidoSex.getCheckedRadioButtonId() == R.id.upload_sex_male ? 0 : 1);

        mLostPersonBean.setLostHeight(mUploadEditHeight.getText().toString());
        mLostPersonBean.setFaceBody(mUploadEditDetail1.getText().toString());
        mLostPersonBean.setClothes(mUploadEditDetail2.getText().toString());
        mLostPersonBean.setOralState(mUploadEditDetail3.getText().toString());
        if (!android.text.TextUtils.isEmpty(mUploadEditBonus.getText().toString())) {
            mLostPersonBean.setBonus(Integer.valueOf(mUploadEditBonus.getText().toString()));
        } else {
            mLostPersonBean.setBonus(0);
        }

        if (mAddressBean != null) {
            mLostPersonBean.setLostPlace(mAddressBean.getFormatAddress());
            mLostPersonBean.setLongitude(mAddressBean.getLongitude());
            mLostPersonBean.setLatitude(mAddressBean.getLatitude());
        }

        if (!formCheck()) {
            return;
        }

        mIsUploading = true;
        Set<String> selectedImage = ((UploadLostActivity) getActivity()).getSelectedImages();
        if (selectedImage != null) {
            for (String path : selectedImage) {
                mLostPersonBean.addFile(path);
            }
        }

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getResources().getString(R.string.publishing));
        pd.setCancelable(false);
        pd.show();
        UploadLostAction.uploadLost(mLostPersonBean, new UploadLostAction.IUploadLostCB() {
            @Override
            public void onUploadLostSuccess(MatchListBean matchListBean) {
                mIsUploading = false;
                UIUtils.showToast(R.string.publish_success);
                getActivity().finish();
            }

            @Override
            public void onUploadLostFailed(int code, String message) {
                pd.dismiss();
                mIsUploading = false;
                UIUtils.showToast(message);
            }
        });
    }

    @Override
    public void onMoreBtnClicked() {
        ((UploadLostActivity) getActivity()).onMoreBtnClicked();
    }

    @Override
    public void onSelectedImageClosed(ImageBean image) {
        ((UploadLostActivity) getActivity()).removeSelectedImage(image.getUri().toString());
    }

    public void syncGallery(Set<ImageBean> images) {
        mGalleryWidget.clear();
        initGalleryWidget(images);
    }

    private void initGalleryWidget(Set<ImageBean> images) {
        if (images != null && mGalleryWidget != null) {
            for (ImageBean image : images) {
                mGalleryWidget.insertImage(image);
            }
        }
    }


    public void syncLocation(AddressBean bean) {
        mAddressBean = bean;
        if (mUploadLocation != null) {
            mUploadLocation.setText(bean.getFormatAddress());
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            Double geoLat = aMapLocation.getLatitude();
            Double geoLng = aMapLocation.getLongitude();
            AddressBean bean = new AddressBean();
            bean.setLatitude(geoLat);
            bean.setLongitude(geoLng);
            bean.setProvince(aMapLocation.getProvince());
            bean.setCity(aMapLocation.getCity());
            bean.setTitle(aMapLocation.getStreet());
            syncLocation(bean);
        } else {
            UIUtils.showToast(getString(R.string.error_locationfailed));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void initRequestLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 100, this);
        mLocationManagerProxy.setGpsEnable(false);
    }

    private void stopRequestLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }
}
