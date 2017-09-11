package com.netease.study.exercise.module.uploadlost;

import android.support.annotation.NonNull;

import com.netease.study.exercise.bean.LostPersonBean;
import com.netease.study.exercise.bean.MatchListBean;
import com.netease.study.exercise.net.HttpMethod;
import com.netease.study.exercise.net.MultiPartRequest;

import static com.netease.study.exercise.utils.DateUtils.dateStr2str;

public class UploadLostHttpTask extends MultiPartRequest {
    public UploadLostHttpTask(@NonNull LostPersonBean lostPersonBean) {
        super();
        mBodyMap.put("lostName", lostPersonBean.getLostName());
        mBodyMap.put("lostTime", dateStr2str(lostPersonBean.getLostTime()));
        mBodyMap.put("lostPlace", lostPersonBean.getLostPlace());
        mBodyMap.put("birth", dateStr2str(lostPersonBean.getBirth()));
        mBodyMap.put("contact", lostPersonBean.getContact());
        mBodyMap.put("lostSex", lostPersonBean.getLostSex().toString());
        mBodyMap.put("longitude", "" + lostPersonBean.getLongitude());
        mBodyMap.put("latitude", "" + lostPersonBean.getLatitude());
        mBodyMap.put("faceBody", lostPersonBean.getFaceBody());
        mBodyMap.put("clothes", lostPersonBean.getClothes());
        mBodyMap.put("oralState", lostPersonBean.getOralState());
        mBodyMap.put("lostHeight", lostPersonBean.getLostHeight());
        mBodyMap.put("bonus", String.valueOf(lostPersonBean.getBonus()));
//        mBodyMap.put("deviceId", ExerciseUtils.getDeviceID());
        mFiles.addAll(lostPersonBean.getFile().values());
    }

    @Override
    public String getApi() {
        return "lost/upload";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return MatchListBean.class;
    }
}
