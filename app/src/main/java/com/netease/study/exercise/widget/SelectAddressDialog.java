package com.netease.study.exercise.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.study.exercise.R;
import com.netease.study.exercise.widget.wheelview.AbstractWheelTextAdapter;
import com.netease.study.exercise.widget.wheelview.OnWheelChangedListener;
import com.netease.study.exercise.widget.wheelview.OnWheelScrollListener;
import com.netease.study.exercise.widget.wheelview.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 更改封面对话框
 *
 * @author ywl
 */
public class SelectAddressDialog extends Dialog implements View.OnClickListener {

    private WheelView mProvince;
    private WheelView mCity;
    private View lyChangeAddress;
    private View lyChangeAddressChild;
    private TextView mOk;
    private TextView mCancel;

    private Context mContext;
    private JSONObject mJsonObj;
    private String[] mProvinceData;
    private Map<String, String[]> mCityDataMap = new HashMap<String, String[]>();

    private ArrayList<String> mArrayProvince = new ArrayList<String>();
    private ArrayList<String> mArrayCity = new ArrayList<String>();
    private AddressTextAdapter mProvinceAdapter;
    private AddressTextAdapter mCityAdapter;

    private String mStrProvince = "四川";
    private String mStrCity = "成都";
    private OnAddressCListener mOnAddressCListener;

    private int mMaxSize = 24;
    private int mMinSize = 14;

    public SelectAddressDialog(Context context) {
        super(context, R.style.ShareDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_address);

        mProvince = (WheelView) findViewById(R.id.wv_address_province);
        mCity = (WheelView) findViewById(R.id.wv_address_city);
        lyChangeAddress = findViewById(R.id.select_address);
        lyChangeAddressChild = findViewById(R.id.select_address_child);
        mOk = (TextView) findViewById(R.id.btn_select_address_ok);
        mCancel = (TextView) findViewById(R.id.btn_select_address_cancel);

        lyChangeAddress.setOnClickListener(this);
        lyChangeAddressChild.setOnClickListener(this);
        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        initJsonData();
        initDatas();
        initProvinces();
        mProvinceAdapter = new AddressTextAdapter(mContext, mArrayProvince, getProvinceItem(mStrProvince), mMaxSize, mMinSize);
        mProvince.setVisibleItems(5);
        mProvince.setViewAdapter(mProvinceAdapter);
        mProvince.setCurrentItem(getProvinceItem(mStrProvince));

        initCitys(mCityDataMap.get(mStrProvince));
        mCityAdapter = new AddressTextAdapter(mContext, mArrayCity, getCityItem(mStrCity), mMaxSize, mMinSize);
        mCity.setVisibleItems(5);
        mCity.setViewAdapter(mCityAdapter);
        mCity.setCurrentItem(getCityItem(mStrCity));

        mProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mProvinceAdapter.getItemText(wheel.getCurrentItem());
                mStrProvince = currentText;
                setTextviewSize(currentText, mProvinceAdapter);
                String[] citys = mCityDataMap.get(currentText);
                initCitys(citys);
                mCityAdapter = new AddressTextAdapter(mContext, mArrayCity, 0, mMaxSize, mMinSize);
                mCity.setVisibleItems(5);
                mCity.setViewAdapter(mCityAdapter);
                mCity.setCurrentItem(0);
            }
        });

        mProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mProvinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mProvinceAdapter);
            }
        });

        mCity.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mCityAdapter.getItemText(wheel.getCurrentItem());
                mStrCity = currentText;
                setTextviewSize(currentText, mCityAdapter);
            }
        });

        mCity.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mCityAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mCityAdapter);
            }
        });
    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.dialog_select_address_item, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }

    public void setAddresskListener(OnAddressCListener onAddressCListener) {
        this.mOnAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        if (v == mOk) {
            if (mOnAddressCListener != null) {
                mOnAddressCListener.onClick(mStrProvince, mStrCity);
            }
        } else if (v == mCancel) {

        } else if (v == lyChangeAddressChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAddressCListener {
        public void onClick(String province, String city);
    }

    /**
     * 从文件中读取地址数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mContext.getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析数据
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceData = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);
                String province = jsonP.getString("p");

                mProvinceData[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("a");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("s");
                        mAreasDatas[k] = area;
                    }
                }
                mCityDataMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 初始化省会
     */
    public void initProvinces() {
        int length = mProvinceData.length;
        for (int i = 0; i < length; i++) {
            mArrayProvince.add(mProvinceData[i]);
        }
    }

    /**
     * 根据省会，生成该省会的所有城市
     *
     * @param citys
     */
    public void initCitys(String[] citys) {
        if (citys != null) {
            mArrayCity.clear();
            int length = citys.length;
            for (int i = 0; i < length; i++) {
                mArrayCity.add(citys[i]);
            }
        } else {
            String[] city = mCityDataMap.get("四川");
            mArrayCity.clear();
            int length = city.length;
            for (int i = 0; i < length; i++) {
                mArrayCity.add(city[i]);
            }
        }
        if (mArrayCity != null && mArrayCity.size() > 0
                && !mArrayCity.contains(mStrCity)) {
            mStrCity = mArrayCity.get(0);
        }
    }

    /**
     * 初始化地点
     *
     * @param province
     * @param city
     */
    public void setAddress(String province, String city) {
        if (province != null && province.length() > 0) {
            this.mStrProvince = province;
        }
        if (city != null && city.length() > 0) {
            this.mStrCity = city;
        }
    }

    /**
     * 返回省会索引，没有就返回默认“四川”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = mArrayProvince.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(mArrayProvince.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            mStrProvince = "四川";
            return 22;
        }
        return provinceIndex;
    }

    /**
     * 得到城市索引，没有返回默认“成都”
     *
     * @param city
     * @return
     */
    public int getCityItem(String city) {
        int size = mArrayCity.size();
        int cityIndex = 0;
        boolean nocity = true;
        for (int i = 0; i < size; i++) {
            System.out.println(mArrayCity.get(i));
            if (city.equals(mArrayCity.get(i))) {
                nocity = false;
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        if (nocity) {
            mStrCity = "成都";
            return 0;
        }
        return cityIndex;
    }

}