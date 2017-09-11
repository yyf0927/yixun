package com.netease.study.exercise.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONType;
import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.DateUtils;

import java.io.Serializable;

/**
 * Created by Asha on 15/7/22.
 * Asha hzqiujiadi@corp.netease.com
 */
@JSONType(ignores = {"firstPhoto"})
public class LostListItemBean implements Serializable {
    private static final long serialVersionUID = 4278404094433904760L;
    /*
    {
        "id": 70,
            "lostName": "小丁",
            "lostSex": "男",
            "lostPlace": "杭州市滨江区网商路599号",
            "year": 0,
            "bonus": 300,
            "describes": "大眼睛 绿色上衣 东北 ",
            "lostTime": "2015-8-03",
            "commentNum": 0,
            "photoName": "http://nos.126.net/mini-02-01/f5abe924-cd9d-4d2f-adde-21aab843861a-1438588074518.jpg,http://nos.126.net/mini-02-01/e8245d91-fb14-436d-a2c7-4132db8a06df-1438588074587.jpg,http://nos.126.net/mini-02-01/2033a3cf-7575-4fb5-989c-a6b43687bc23-1438588074705.jpg,http://nos.126.net/mini-02-01/e37f7878-9f40-4674-844c-c33ceb68ada4-1438588074799.jpg,http://nos.126.net/mini-02-01/d0a5b126-5b2d-4675-959b-5ed2524c0a58-1438588074842.jpg,http://nos.126.net/mini-02-01/b40b2b33-39fd-4c39-b756-ff256dbff7cb-1438588074887.jpg",
            "distance": 0,
            "distance": 8534.833816015496,
            "weight": 20000000000.466667
    },

    */

    private boolean mFlag;
    private int mType;
    private int mId;
    private String mLostName;
    private String mLostSex;
    private String mLostPlace;
    private int mYear;
    //悬赏
    private int mBonus;
    private String mDescribes;
    private int mCommentNum;
    private String mPhotoName;
    private String[] mPhotos;

    private double mDistance;
    private String mLostTime;
    private String mDistanceStr;

    private boolean mShowTime;
    private String mTimeStr;
    private long mTimeStamp;
    private String mFirstPhoto;


    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getLostName() {
        return mLostName;
    }

    public void setLostName(String lostName) {
        this.mLostName = lostName;
    }

    public String getLostSex() {
        return mLostSex;
    }

    public void setLostSex(String lostSex) {
        this.mLostSex = lostSex;
    }

    public String getLostPlace() {
        return mLostPlace;
    }

    public void setLostPlace(String lostPlace) {
        this.mLostPlace = lostPlace;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public int getBonus() {
        return mBonus;
    }

    public void setBonus(int bonus) {
        this.mBonus = bonus;
    }

    public String getDescribes() {
        return mDescribes;
    }

    public void setDescribes(String describes) {
        this.mDescribes = describes;
        if (describes != null) {
            if (android.text.TextUtils.isEmpty(describes.trim())) {
                this.mDescribes = AppProfile.getContext().getResources().getString(R.string.nothing);
            }
        } else {
            this.mDescribes = AppProfile.getContext().getResources().getString(R.string.nothing);
        }
    }

    public int getCommentNum() {
        return mCommentNum;
    }

    public void setCommentNum(int commentNum) {
        this.mCommentNum = commentNum;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
        if (mPhotos == null && !TextUtils.isEmpty(photoName)) {
            mPhotos = photoName.split(",");
            for (int i = 0; i < mPhotos.length; i++) {
                if (i == 0) mFirstPhoto = mPhotos[0];
                mPhotos[i] = mPhotos[i] + "?thumbnail=300x300";
            }
        }
    }

    public String getLostTime() {
        return mLostTime;
    }

    public void setLostTime(String lostTime) {
        this.mLostTime = lostTime;
        if (!android.text.TextUtils.isEmpty(lostTime)) {
            mTimeStamp = DateUtils.dateStr2TimeStamp(lostTime);
        }
    }

    public double getDistance() {
        return mDistance;
    }

    public String getDistanceStr() {
        return mDistanceStr;
    }

    public void setDistance(double distance) {
        this.mDistance = distance;
        if (distance < 1) {
            distance *= 1000;
        }
        mDistanceStr = AppProfile.getContext().getResources().getString(R.string.distance01km, distance);
    }

    public boolean isShowTime() {
        return mShowTime;
    }

    public void setShowTime(boolean showTime) {
        this.mShowTime = showTime;
    }

    public String getTimeStr() {
        return mTimeStr;
    }

    public void setTimeStr(String timeStr) {
        this.mTimeStr = timeStr;
    }

    public String[] getPhotos() {
        return mPhotos;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public String getFirstPhoto() {
        return mFirstPhoto;
    }

    public void setFirstPhoto(String firstPhoto) {
        this.mFirstPhoto = firstPhoto;
    }

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean flag) {
        this.mFlag = flag;
    }
}
