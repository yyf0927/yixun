package com.netease.study.exercise;

import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;

import com.netease.study.exercise.bean.FriendsListBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.friend.GetFriendsHttpTask;
import com.netease.study.exercise.module.login.LoginAction;
import com.netease.study.exercise.module.login.LoginHttpTask;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.module.persondetail.FriendOperationHttpTask;
import com.netease.study.exercise.net.HttpResponse;
import com.sina.weibo.sdk.utils.MD5;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class FriendInstrumentationTest extends TestCase {

    @Before
    @UiThreadTest
    public void setUp() throws Exception {
    }

    @Test
    public void testFriendOperation() {
        try {
            //未登录
            LoginAction.logout();
            //测试userid边界
            FriendOperationHttpTask task = new FriendOperationHttpTask(-1, true);
            Assert.assertEquals(task.execute().code.intValue(), 4);
            task = new FriendOperationHttpTask(0, true);
            Assert.assertEquals(task.execute().code.intValue(), 4);
            //测试不存在的userid
            task = new FriendOperationHttpTask(Long.MAX_VALUE, true);
            Assert.assertEquals(task.execute().code.intValue(), 4);
            //测试存在userid，取消关注
            task = new FriendOperationHttpTask(38, false);
            Assert.assertEquals(task.execute().code.intValue(), 4);
            //反复关注
            task = new FriendOperationHttpTask(38, true);
            Assert.assertEquals(task.execute().code.intValue(), 4);

            //已登录情况
            UserProfile.setUser((UserBean) new LoginHttpTask("13012345678", MD5.hexdigest("123456")).execute().data);
            //测试userid边界
            task = new FriendOperationHttpTask(-1, true);
            Assert.assertEquals(task.execute().code.intValue(), 2);
            task = new FriendOperationHttpTask(0, true);
            Assert.assertEquals(task.execute().code.intValue(), 2);
            //测试不存在的userid
            task = new FriendOperationHttpTask(Long.MAX_VALUE, true);
            Assert.assertEquals(task.execute(), null);
            //测试存在userid，确保先取消关注再关注
            task = new FriendOperationHttpTask(38, false);
            task.execute();
            //反复取消关注
            task = new FriendOperationHttpTask(38, false);
            Assert.assertEquals(task.execute().code.intValue(), 1);
            //关注
            task = new FriendOperationHttpTask(38, true);
            Assert.assertEquals(task.execute().code.intValue(), 1);
            //反复关注
            task = new FriendOperationHttpTask(38, true);
            Assert.assertEquals(task.execute().code.intValue(), 2);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetFriendList() {
        try {
            //未登录
            LoginAction.logout();
            GetFriendsHttpTask task = new GetFriendsHttpTask();
            HttpResponse response = task.execute();
            Assert.assertEquals(response.code.intValue(), 4);

            //已登录情况
            UserProfile.setUser((UserBean) new LoginHttpTask("13012345678", MD5.hexdigest("123456")).execute().data);
            task = new GetFriendsHttpTask();
            response = task.execute();
            Assert.assertEquals(response.code.intValue(), 1);
            Assert.assertEquals(response.data.getClass(), FriendsListBean.class);
            Assert.assertTrue(((FriendsListBean) response.data).getRelation().size() - 1 >= 0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
