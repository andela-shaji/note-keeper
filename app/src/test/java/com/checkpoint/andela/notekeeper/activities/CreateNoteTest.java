package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.checkpoint.andela.notekeeper.BuildConfig;
import com.checkpoint.andela.notekeeper.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class CreateNoteTest {
    private CreateNote createNoteActivity;



    @Before
    public void setUp() throws Exception {
        createNoteActivity = Robolectric.buildActivity(CreateNote.class).create().get();

    }

    @Test
    public void testOnCreate() throws Exception {
        View view = createNoteActivity.findViewById(R.id.create_note_layout);
        assertNotNull(view);
    }

    @Test
    public void testCreateNote() throws Exception {
        ShadowActivity activityShadow = Shadows.shadowOf(createNoteActivity);
        activityShadow.onBackPressed();
        assertTrue(activityShadow.isFinishing());
    }


}