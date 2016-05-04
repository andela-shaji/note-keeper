package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;

import com.checkpoint.andela.notekeeper.BuildConfig;
import com.checkpoint.andela.notekeeper.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class DashBoardTest {
    private DashBoard dashBoardActivity;

    @Before
    public void setUp() throws Exception {
        dashBoardActivity = Robolectric.buildActivity(DashBoard.class).create().get();
    }

    @Test
    public void testOnCreate() throws Exception {
        View view = dashBoardActivity.findViewById(R.id.dashboard_layout);
        assertNotNull(view);
    }

    @Test
    public void testOnClick() throws Exception {
        ImageButton createNote = (ImageButton) dashBoardActivity.findViewById(R.id.dashboard_create_note);
        if (createNote != null) {
            createNote.performClick();
        }
        Intent intent = new Intent(dashBoardActivity, CreateNote.class);
        assertEquals(CreateNote.class.getCanonicalName(), intent.getComponent().getClassName());

        ImageButton trashNote = (ImageButton) dashBoardActivity.findViewById(R.id.dashboard_trash_note);
        if (trashNote != null) {
            trashNote.performClick();
        }
        Intent trashIntent = new Intent(dashBoardActivity, Trash.class);
        assertEquals(Trash.class.getCanonicalName(), trashIntent.getComponent().getClassName());
    }

}