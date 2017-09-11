package com.netease.study.exercise.module.login;

import android.support.annotation.NonNull;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.bean.TokenBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.net.HttpCallback;

/**
 * Created by zw on 16/10/21.
 */
public class LoginAction {
    public static final int LOGIN_CODE = 100;

    public static void resetPwd(String mobile, String pwd, final IResetPwdCB cb) {
        ResetPwdHttpTask task = new ResetPwdHttpTask(mobile, pwd);
        task.enqueue(new HttpCallback() {

            public void onResponse(BaseRequest request, Object data) {
                if (cb != null) {
                    cb.onResetSuccess();
                }
            }

            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (cb != null) {
                    cb.onResetFailed(code, message);
                }
            }
        });
    }

    public static void login(final String mobile, String pwd, final ILoginCB cb) {
        LoginHttpTask task = new LoginHttpTask(mobile, pwd);
        task.enqueue(new HttpCallback() {
            public void onResponse(BaseRequest request, Object data) {
                UserBean bean = (UserBean) data;
                if (bean != null) {
                    bean.setMobile(mobile);
                }
                UserProfile.setUser(bean);
                if (cb != null) {
                    cb.onLoginSuccess(bean);
                }
            }

            /**
             *
             * @param request
             * @param e 异常：网络异常，json解析异常
             * @param code  业务code，若httpcode为200，后两个参数代表业务参数；
             * @param message  业务message
             */
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (cb != null) {
                    cb.onLoginFailed(code, message);
                }
            }
        });
    }


    public static void register(final String mobile, String username, String pwd, final ILoginCB cb) {
        RegisterHttpTask task = new RegisterHttpTask(mobile, pwd, true, username);
        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                UserBean bean = (UserBean) data;
                if (bean != null)
                    bean.setMobile(mobile);
                UserProfile.setUser(bean);
                if (cb != null) {
                    cb.onLoginSuccess(bean);
                }
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (cb != null) {
                    cb.onLoginFailed(code, message);
                }

            }
        });

    }

    public static void tokenLogin(@NonNull String token) {
        AppProfile.getHttpClientWrapper().setToken(token);
        TokenLoginHttpTask task = new TokenLoginHttpTask();
        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                TokenBean bean = (TokenBean) data;
                UserProfile.updateTokenUser(bean);
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                //自动登录失败，清除本地用户数据
                UserProfile.save(null);
            }
        });

    }

    public static void logout() {
        UserProfile.setUser(null);
    }

    public static interface IResetPwdCB {
        public void onResetSuccess();

        public void onResetFailed(int code, String message);
    }

    public static interface ILoginCB {
        public void onLoginSuccess(UserBean userBean);

        public void onLoginFailed(int code, String message);
    }

}
