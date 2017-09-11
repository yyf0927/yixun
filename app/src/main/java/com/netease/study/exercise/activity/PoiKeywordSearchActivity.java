package com.netease.study.exercise.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.AddressBean;
import com.netease.study.exercise.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class PoiKeywordSearchActivity extends BaseActivity implements
        OnMarkerClickListener, InfoWindowAdapter, TextWatcher,
        OnPoiSearchListener, OnClickListener
        , AdapterView.OnItemClickListener
        , GeocodeSearch.OnGeocodeSearchListener {
    public static final int REQUEST_CODE_POI = 313;
    public static final String POI_PARAM_KEY = "poi_param_key";

    private AutoCompleteTextView mSearchText;// 输入搜索关键字
    private ProgressDialog mProgDialog = null;// 搜索时进度条
    private Button mConfirmButton;

    private AMap mAMap;
    private String mKeyWord = "";// 要输入的poi搜索关键字
    private PoiResult mPoiResult; // poi返回的结果
    private int mCurrentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query mQuery;// Poi查询条件类
    private PoiSearch mPoiSearch;// POI搜索
    private Marker mSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poikeywordsearch);
        setTitle(R.string.select_location);
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.poi_map)).getMap();
            setUpMap();
        }
    }

    /**
     * 设置页面监听
     */
    private void setUpMap() {
        Button searButton = (Button) findViewById(R.id.poi_searchButton);
        searButton.setOnClickListener(this);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.poi_keyWord);
        mSearchText.addTextChangedListener(this);// 添加文本输入框监听事件
        mSearchText.setOnItemClickListener(this);
        mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        mConfirmButton = (Button) findViewById(R.id.poi_confirm);
        mConfirmButton.setEnabled(false);
        mConfirmButton.setOnClickListener(this);

        Intent i = getIntent();
        AddressBean bean = intent2Address(i);
        if (bean != null) {
            moveTo(bean);
        }
    }

    /**
     * 点击搜索按钮
     */
    public void searchButton() {
        searchKeywords(mSearchText.getText().toString().trim());
    }

    private void searchKeywords(String keyword) {
        mKeyWord = keyword;
        if ("".equals(keyword)) {
            UIUtils.showToast(R.string.input_keyword);
        } else {
            doSearchQuery("");
        }
    }

    /**
     * 点击下一页按钮
     */
    public void nextButton() {
        if (mQuery != null && mPoiSearch != null && mPoiResult != null) {
            if (mPoiResult.getPageCount() - 1 > mCurrentPage) {
                mCurrentPage++;
                mQuery.setPageNum(mCurrentPage);// 设置查后一页
                mPoiSearch.searchPOIAsyn();
            } else {
                UIUtils.showToast(R.string.noresult);
            }
        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (mProgDialog == null) {
            mProgDialog = new ProgressDialog(this);
        }
        mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDialog.setIndeterminate(false);
        mProgDialog.setCancelable(false);
        mProgDialog.setMessage(getString(R.string.searching, mKeyWord));
        mProgDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (mProgDialog != null) {
            mProgDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String city) {
        InputMethodManager iputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        iputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        showProgressDialog();// 显示进度框
        mCurrentPage = 0;
        mQuery = new PoiSearch.Query(mKeyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery.setPageSize(10);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(mCurrentPage);// 设置查第一页

        mPoiSearch = new PoiSearch(this, mQuery);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIAsyn();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mConfirmButton.setText(R.string.confirm);
        mConfirmButton.setEnabled(true);
        mConfirmButton.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        mSelectedMarker = marker;

        View view = getLayoutInflater().inflate(R.layout.widget_poikeywordsearch_uri, null);
        TextView title = (TextView) view.findViewById(R.id.poi_title);
        title.setText(marker.getTitle());
        TextView snippet = (TextView) view.findViewById(R.id.poi_snippet);
        snippet.setText(marker.getSnippet());
        return view;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    long lastPress = System.currentTimeMillis();

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();

        if (System.currentTimeMillis() - lastPress > 500) {
            lastPress = System.currentTimeMillis();
            Inputtips inputTips = new Inputtips(PoiKeywordSearchActivity.this,
                    new Inputtips.InputtipsListener() {
                        @Override
                        public void onGetInputtips(List<Tip> tipList, int rCode) {
                            if (rCode == 0) {// 正确返回
                                List<String> listString = new ArrayList<String>();
                                for (int i = 0; i < tipList.size(); i++) {
                                    listString.add(tipList.get(i).getName());
                                }
                                ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        R.layout.widget_poiroute_inputs, listString);
                                mSearchText.setAdapter(aAdapter);
                                aAdapter.notifyDataSetChanged();
                            }
                        }
                    });
            try {
                inputTips.requestInputtips(newText, "");
                // 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号
            } catch (AMapException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * POI详情查询回调方法
     */
    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int rCode) {

    }

    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(mQuery)) {// 是否是同一条
                    mPoiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = mPoiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = mPoiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        mAMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                        Marker marker = mAMap.getMapScreenMarkers().get(0);
                        marker.showInfoWindow();
                        onMarkerClick(marker);
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12);
                        mAMap.animateCamera(update);

                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
//                        showSuggestCity(suggestionCities);
                    } else {
                        UIUtils.showToast(R.string.noresult);
                    }
                }
            } else {
                UIUtils.showToast(R.string.noresult);
            }
        } else if (rCode == 27) {
            UIUtils.showToast(R.string.neterror);
        } else if (rCode == 32) {
            UIUtils.showToast(R.string.unknown_error);
        }

    }

    /**
     * Button点击事件回调方法
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.poi_searchButton:
                searchButton();
                break;
            case R.id.poi_confirm:
                onConfirmResult();
                break;
            default:
                break;
        }
    }

    private void onConfirmResult() {
        double latitude = mSelectedMarker.getPosition().latitude;
        double longitude = mSelectedMarker.getPosition().longitude;
        LatLonPoint point = new LatLonPoint(latitude, longitude);
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(point, 10, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        showProgressDialog();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        searchKeywords(obj.toString());
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        dissmissProgressDialog();

        regeocodeResult.getRegeocodeAddress().getFormatAddress();
        Log.e("onRegeocodeSearched poi", regeocodeResult.toString());

        AddressBean bean = new AddressBean();
        bean.setLatitude(mSelectedMarker.getPosition().latitude);
        bean.setLongitude(mSelectedMarker.getPosition().longitude);
        bean.setCity(regeocodeResult.getRegeocodeAddress().getCity());
        bean.setProvince(regeocodeResult.getRegeocodeAddress().getProvince());
        bean.setTitle(mSelectedMarker.getTitle());

        finalStep(bean);
    }

    private void finalStep(AddressBean bean) {
        Intent intent = new Intent();
        intent.putExtra(POI_PARAM_KEY, JSON.toJSONString(bean));
        setResult(RESULT_OK, intent);
        this.finish();
    }

    private void moveTo(AddressBean bean) {
        LatLng latlng = new LatLng(bean.getLatitude(), bean.getLongitude());
        Marker marker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latlng).title(bean.getTitle())
                .snippet(bean.getFormatAddress()));
        marker.showInfoWindow();
        onMarkerClick(marker);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, 11);
        mAMap.moveCamera(update);
    }


    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        //Log.e("onGeocodeSearched poi",geocodeResult.toString());
    }

    public static void launchActivityForResult(Activity context, AddressBean addressBean) {
        Intent i = new Intent(context, PoiKeywordSearchActivity.class);
        if (addressBean != null) {
            i.putExtra(POI_PARAM_KEY, JSON.toJSONString(addressBean));
        }
        context.startActivityForResult(i, REQUEST_CODE_POI);
    }

    public static AddressBean intent2Address(Intent intent) {
        String json = intent.getStringExtra(POI_PARAM_KEY);
        AddressBean bean = JSON.parseObject(json, AddressBean.class);
        return bean;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        this.finish();
    }
}
