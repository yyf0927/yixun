package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.fragment.LoginFragment;
import com.netease.study.exercise.fragment.RegisterFragment;
import com.netease.study.exercise.fragment.ResetPasswordFragment;

/**
 * Created by zw on 16/10/21.
 */
public class EntranceActivity extends BaseActivity {
    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;
    private ResetPasswordFragment mRePwdFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        mLoginFragment = new LoginFragment();
        mRegisterFragment = new RegisterFragment();
        mRePwdFragment = new ResetPasswordFragment();
        switchLoginFragment();
    }


    private void switchLoginFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, mLoginFragment);
        transaction.commit();
    }

    public void switchRegisterFragment() {
        switchFragment(mRegisterFragment);
    }


    public void switchRePwdFragment() {
        switchFragment(mRePwdFragment);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    protected void onNavigationBackButtonPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackEntryCount==0)
            finish();
        else{
            getSupportFragmentManager().popBackStack();
        }
    }

    public static void launch(Context context) {
        launch(context, 0);
    }

    public static void launch(Context context, int code) {
        Intent intent = new Intent();
        intent.setClass(context, EntranceActivity.class);
        if (code == 0) {
            context.startActivity(intent);
        } else {
            ((BaseActivity) context).startActivityForResult(intent, code);
        }
    }
}
