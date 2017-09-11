package com.netease.study.exercise.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.EntranceActivity;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.utils.PhoneNumberHelper;
import com.netease.study.exercise.utils.ToastWrapper;
import com.sina.weibo.sdk.utils.MD5;

/**
 * Created by zw on 16/10/29.
 */
public class LoginFragment extends LoginBaseFragment implements View.OnClickListener {
    private View mRootView;
    private EditText mEdtMobile;
    private EditText mEdtPwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login, container, false);
        return mRootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEdtMobile = (EditText) mRootView.findViewById(R.id.edtLoginMobile);
        mEdtPwd = (EditText) mRootView.findViewById(R.id.edtLoginPwd);
        mRootView.findViewById(R.id.btn_login).setOnClickListener(this);
        mRootView.findViewById(R.id.txtForgetPwd).setOnClickListener(this);
        mRootView.findViewById(R.id.txtRegister).setOnClickListener(this);
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_login: {

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

                LoginAction.login(mobile, MD5.hexdigest(pwd), this);
            }

            break;
            case R.id.txtForgetPwd: {
                ((EntranceActivity) getActivity()).switchRePwdFragment();
            }
            break;
            case R.id.txtRegister: {
                ((EntranceActivity) getActivity()).switchRegisterFragment();
                ;

            }
            break;
        }
    }


}
