package com.netease.study.exercise;

import android.support.test.annotation.UiThreadTest;

import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.LoginHttpTask;
import com.netease.study.exercise.net.HttpClientWrapper;
import com.netease.study.exercise.net.HttpResponse;
import com.sina.weibo.sdk.utils.MD5;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.concurrent.CountDownLatch;
/**
 * Created by zw on 16/11/2.
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentationTest {
    private HttpClientWrapper wrapper;

    @Before
    @UiThreadTest
    public void setUp() throws Exception {
        wrapper = new HttpClientWrapper(InstrumentationRegistry.getContext());
    }

    @Test
    public void testLoginHttp() {
        LoginHttpTask taks = new LoginHttpTask("13012345678", MD5.hexdigest("123456"));
        HttpResponse<UserBean> response = null;
        try {
            response = taks.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(response);
        assertNotNull(response.data);
    }

    @Test
    public void testAsyncLogin(){
        final CountDownLatch latch = new CountDownLatch(1);
        LoginAction.login("13012345678", MD5.hexdigest("123456"), new LoginAction.ILoginCB() {
            @Override
            public void onLoginSuccess(UserBean userBean) {
                latch.countDown();
                assertNotNull(userBean);
            }

            @Override
            public void onLoginFailed(int code, String message) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
