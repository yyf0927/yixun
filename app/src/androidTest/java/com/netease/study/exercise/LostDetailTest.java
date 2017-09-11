package com.netease.study.exercise;

import android.support.test.runner.AndroidJUnit4;

import com.netease.study.exercise.bean.LostDetailBean;
import com.netease.study.exercise.bean.MoreCommentBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.LoginHttpTask;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.module.lostdetail.LostDetailAction;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.net.HttpCallback;
import com.sina.weibo.sdk.utils.MD5;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by vincent on 09/12/2016.
 */

@RunWith(AndroidJUnit4.class)
public class LostDetailTest extends TestCase {

    @Test
    public void testGetDetail() {
        final CountDownLatch latch = new CountDownLatch(1);

        LostDetailAction.getLostDetail(42, new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                latch.countDown();

                assertTrue(data instanceof LostDetailBean);
                assertNotNull(((LostDetailBean) data).getSearchInfoDetail());
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                fail();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCommentWhenLogin() {
        final CountDownLatch latch = new CountDownLatch(1);

        //Login
        login();

        LostDetailAction.getComment(42, 1, new HttpCallback() {

            @Override
            public void onResponse(BaseRequest request, Object data) {
                latch.countDown();

                assertTrue(data instanceof MoreCommentBean);
                assertNotNull(((MoreCommentBean) data).getCommentList());
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                fail();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCommentWhenLogout() {
        final CountDownLatch latch = new CountDownLatch(1);

        //Logout
        LoginAction.logout();

        LostDetailAction.getComment(42, 1, new HttpCallback() {

            @Override
            public void onResponse(BaseRequest request, Object data) {
                fail();
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                latch.countDown();
                assertEquals(code, 4);
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddCommentWhenLogin() {
        final CountDownLatch latch = new CountDownLatch(1);

        //Login
        login();

        LostDetailAction.addComment(42, "Test", 0, new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                latch.countDown();
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                fail();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddCommentWhenLogout() {
        final CountDownLatch latch = new CountDownLatch(1);

        //Logout
        LoginAction.logout();

        LostDetailAction.addComment(42, "Test", 0, new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                fail();
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                latch.countDown();
                assertEquals(code, 4);
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        try {
            UserProfile.setUser((UserBean) new LoginHttpTask("13012345678", MD5.hexdigest("123456")).execute().data);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
