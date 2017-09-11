package com.netease.study.exercise.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.utils.ToastWrapper;

/**
 * Created by zw on 16/11/22.
 */
public abstract class LoginBaseFragment extends Fragment implements LoginAction.ILoginCB {

    public void onLoginSuccess(UserBean userBean) {
        Activity entryActivity = getActivity();
        if (entryActivity != null) {
            entryActivity.setResult(LoginAction.LOGIN_CODE);
            entryActivity.finish();
        }
    }

    public void onLoginFailed(int code, String message) {
        ToastWrapper.makeShortToast(message);
    }
}
