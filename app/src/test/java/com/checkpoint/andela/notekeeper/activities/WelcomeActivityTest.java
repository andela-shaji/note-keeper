package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Build;

import com.checkpoint.andela.notekeeper.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class WelcomeActivityTest {

    @Test
    public void testOnCreateView() {
        WelcomeActivity activity = Robolectric.buildActivity(WelcomeActivity.class).create().get();

        Intent intent = new Intent(activity, DashBoard.class);
        assertEquals(DashBoard.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}