package com.netease.study.exercise.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.netease.study.exercise.R;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.utils.PhoneNumberHelper;
import com.netease.study.exercise.utils.ToastWrapper;
import com.sina.weibo.sdk.utils.MD5;

/**
 * Created by zw on 16/10/21.
 */
public class ResetPasswordFragment extends BaseFragment implements View.OnClickListener, LoginAction.IResetPwdCB {
    private EditText mEdtMobile;
    private EditText mEdtPwd;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_resetpwd, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEdtMobile = (EditText) mRootView.findViewById(R.id.edtRePwdMobile);
        mEdtPwd = (EditText) mRootView.findViewById(R.id.edtRePwd);
        mRootView.findViewById(R.id.btn_rePwd).setOnClickListener(this);
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_rePwd) {
            String mobile = mEdtMobile.getText().toString();
            String pwd = mEdtPwd.getText().toString();

            if (!PhoneNumberHelper.isMobile(mobile)) {
                ToastWrapper.makeShortToast(R.string.login_mobile_hint);
                mEdtMobile.requestFocus();
                return;
            }


            if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
                ToastWrapper.makeShortToast(R.string.login_pwd_too_short);
                mEdtPwd.requestFocus();
                return;
            }

            if (pwd.length() > 20) {
                ToastWrapper.makeShortToast(R.string.login_pwd_too_long);
                mEdtPwd.requestFocus();
                return;
            }
            LoginAction.resetPwd(mobile, MD5.hexdigest(pwd), this);
        }
    }

    public void onResetSuccess() {
        ToastWrapper.makeLongToast(R.string.login_reset_pwd_success);
        //密码修改成功
        FragmentManager fm = this.getFragmentManager();
        fm.popBackStack();

    }

    public void onResetFailed(int code, String message) {
        //密码修改失败
        ToastWrapper.makeLongToast(message);
    }


}
