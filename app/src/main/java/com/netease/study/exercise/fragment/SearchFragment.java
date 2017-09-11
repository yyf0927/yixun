package com.netease.study.exercise.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.SearchResultActivity;
import com.netease.study.exercise.bean.SearchFilterBean;
import com.netease.study.exercise.utils.DateUtils;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.widget.SelectAddressDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuqijun on 12/4/16.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private final static String DEFAULT_PROVINCE = "北京";
    private final static String DEFAULT_CITY = "东城区";

    private RadioButton mGenderMale;
    private EditText mAgeStart;
    private EditText mAgeEnd;
    private Button mLostDate;
    private Button mLostPlace;

    private String mProvince;
    private String mCity;
    private SearchFilterBean mSearchFilterBean = new SearchFilterBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        initView(view);
        return view;
    }

    private void initView(View rootView) {
        mGenderMale = (RadioButton) rootView.findViewById(R.id.search_missing_sex_male);
        mAgeStart = (EditText) rootView.findViewById(R.id.search_missing_age_start_et);
        mAgeEnd = (EditText) rootView.findViewById(R.id.search_missing_age_end_et);
        mLostDate = (Button) rootView.findViewById(R.id.search_missing_lost_time_bt);
        mLostPlace = (Button) rootView.findViewById(R.id.search_missing_lost_place_bt);
        Button screening = (Button) rootView.findViewById(R.id.search_missing_screening_bt);
        screening.setOnClickListener(this);
        mLostDate.setOnClickListener(this);
        mLostPlace.setOnClickListener(this);
        mAgeStart.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_missing_lost_time_bt:
                lostDateSet();
                break;
            case R.id.search_missing_lost_place_bt:
                lostAddressSet();
                break;
            case R.id.search_missing_screening_bt:
                screening();
                break;
        }
    }

    private void lostAddressSet() {
        SelectAddressDialog selectAddressDialog = new SelectAddressDialog(getActivity());
        selectAddressDialog.setAddress(DEFAULT_PROVINCE, DEFAULT_CITY);
        selectAddressDialog.show();
        selectAddressDialog
                .setAddresskListener(new SelectAddressDialog.OnAddressCListener() {

                    @Override
                    public void onClick(String province, String city) {
                        mProvince = province;
                        mCity = city;
                        mLostPlace.setText(mProvince + " " + mCity);
                    }
                });
    }


    private void lostDateSet() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.format(Locale.US, "%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                Date current = DateUtils.str2Date(date);

                if (current != null && current.getTime() < System.currentTimeMillis()) {
                    date = DateUtils.dateStr2strShort(date);
                    mLostDate.setText(date);
                } else {
                    UIUtils.showToast(R.string.errordate);
                }
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        dpd.show();
    }

    private void screening() {
        mSearchFilterBean.setType(0);
        mSearchFilterBean.setSexType(mGenderMale.isChecked() ? 0 : 1);
        mSearchFilterBean.setStartYear(mAgeStart.getText().toString());
        mSearchFilterBean.setEndYear(mAgeEnd.getText().toString());
        mSearchFilterBean.setLostYearAndMonth(mLostDate.getText().toString());
        mSearchFilterBean.setLostProvinceAndCity(mLostPlace.getText().toString());

        SearchResultActivity.launch(getActivity(), mSearchFilterBean);
    }
}
