package com.netease.study.exercise.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.R;
import com.netease.study.exercise.adapter.ViewPagerAdapter;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.UserProfile;

import java.util.ArrayList;

public class StartActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        autoLogin();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chargeSharePreference();
            }
        }, 2000);
    }

    //判定是否可以登录
    private void autoLogin(){
        UserBean userBean  =   UserProfile.loadLocalUser();
        if(userBean!=null&& !TextUtils.isEmpty(userBean.getToken())){
            //只发请求，不关心结果
            LoginAction.tokenLogin(userBean.getToken());
        }
    }

    private void chargeSharePreference() {
        SharedPreferences sp = getSharedPreferences(ExerciseConst.MAIN_PREFERENCE_FILE, Activity.MODE_PRIVATE);
        boolean isFirstStart = sp.getBoolean(ExerciseConst.PREFERENCE_KEY.FIRST_START_APP, true);
        if (isFirstStart) {
            initViewAndData();
        } else {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void initViewAndData() {
        ViewPager startViewPager = (ViewPager) findViewById(R.id.start_viewpager);
        ArrayList<View> views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        ImageView vpStart01 = new ImageView(this);
        vpStart01.setScaleType(ImageView.ScaleType.CENTER_CROP);
        vpStart01.setImageResource(R.drawable.splash_01);
        ImageView vpStart02 = new ImageView(this);
        vpStart02.setScaleType(ImageView.ScaleType.CENTER_CROP);
        vpStart02.setImageResource(R.drawable.splash_02);
        views.add(vpStart01);
        views.add(vpStart02);
        View vpStart03 = inflater.inflate(R.layout.viewpager_start_03, null);
        views.add(vpStart03);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        startViewPager.setAdapter(viewPagerAdapter);
        startViewPager.setCurrentItem(0);
        Button startNowGoBtn = (Button) vpStart03.findViewById(R.id.start_now_go_bt);
        startNowGoBtn.setOnClickListener(this);
        startViewPager.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_now_go_bt:
                saveStartSharedPreferenceFlag();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void saveStartSharedPreferenceFlag() {
        SharedPreferences sp = getSharedPreferences(ExerciseConst.MAIN_PREFERENCE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ExerciseConst.PREFERENCE_KEY.FIRST_START_APP, false);
        editor.apply();
    }
}
