package com.netease.study.exercise;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import com.netease.study.exercise.activity.EntranceActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EntryActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<EntranceActivity> mActivityRule = new ActivityTestRule<>(
            EntranceActivity.class);

    @Test
    public void testLoginBtn() {
        onView(withId(R.id.edtLoginMobile)).perform(typeText("13012345678"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.edtLoginMobile)).check(matches(withText("13012345678")));
    }

}
