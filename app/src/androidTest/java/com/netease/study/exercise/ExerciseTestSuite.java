package com.netease.study.exercise;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by liangjian on 16/12/6.
 */

public class ExerciseTestSuite extends TestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(FriendInstrumentationTest.class);
        suite.addTestSuite(LostDetailTest.class);
        return suite;
    }
}
