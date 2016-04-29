package com.checkpoint.andela.notekeeper.activities;

import android.os.Build;
import android.view.View;

import com.checkpoint.andela.notekeeper.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AppSettingsTest {
    private AppSettings settings;

    @Before
    public void setUp() throws Exception {
        settings = Robolectric.buildActivity(AppSettings.class).create().get();
    }

    @Test
    public void testOnPostCreate() throws Exception {
        View view = settings.findViewById(android.R.id.list);
        assertNotNull(view);
    }

    @Test
    public void testOnBackPressed() throws Exception {
        ShadowActivity shadowActivity = Shadows.shadowOf(settings);
        shadowActivity.onBackPressed();
        assertTrue(shadowActivity.isFinishing());
    }
}