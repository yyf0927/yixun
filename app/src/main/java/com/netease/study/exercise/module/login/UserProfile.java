package com.netease.study.exercise.module.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.bean.TokenBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.net.HttpClientWrapper;

/**
 * Created by zw on 16/10/23.
 */
public class UserProfile {

    public static final String USER_PREFERENCE_FILE = "user";
    public static final String SP_USER = "userbean";
    private static UserBean sUser;

    /**
     * 加载本地用户信息，但未登录
     *
     * @return
     */
    public static UserBean loadLocalUser() {
        SharedPreferences sp = AppProfile.getContext().getSharedPreferences(USER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        String jsonUser = sp.getString(SP_USER, null);
        if (!TextUtils.isEmpty(jsonUser)) {
            return JSONObject.parseObject(jsonUser, UserBean.class);
        }
        return null;
    }

    public static UserBean getUser() {
        return sUser;
    }

    public static void setUser(UserBean value) {
        HttpClientWrapper wrapper = AppProfile.getHttpClientWrapper();
        if (wrapper != null) {
            if (value == null) {
                wrapper.setToken(HttpClientWrapper.DEFUALT_TOKEN);
            } else {
                wrapper.setToken(value.getToken());
            }
        }
        save(value);
        sUser = value;
    }

    /**
     * 自动登录协议设计的不够合理，客户端再做一次适配
     */
    public static void updateTokenUser(TokenBean bean) {
        UserBean localUser = loadLocalUser();
        localUser.setUserId(bean.getUserId());
        localUser.setSign(bean.getSign());
        localUser.setUserName(bean.getUserName());
        localUser.setPhotoName(bean.getPhotoName());
        localUser.setContact(bean.getContact());
        localUser.setUserId(bean.getUserId());
        localUser.setPoints(bean.getPoints());
        save(localUser);
        //更新自动登录的信息
        sUser = localUser;
    }


    public static boolean isLogin() {
        return sUser != null && sUser.getToken() != null;
    }

    public static void save(UserBean value) {
        SharedPreferences.Editor editor = AppProfile.getContext().getSharedPreferences(USER_PREFERENCE_FILE, Context.MODE_PRIVATE).edit();
        if (value == null) {
            editor.clear();
        } else {
            String str = JSON.toJSONString(value);
            editor.putString(SP_USER, str);
        }
        editor.apply();
    }


}
