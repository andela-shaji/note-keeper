package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.checkpoint.andela.notekeeper.BuildConfig;
import com.checkpoint.andela.notekeeper.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class ReadNoteTest {
    private ReadNote readNoteActivity;

    @Before
    public void setUp() throws Exception {
        readNoteActivity = Robolectric.setupActivity(ReadNote.class);
    }

    @Test
    public void testOnCreate() throws Exception {
        View view = readNoteActivity.findViewById(R.id.create_note_layout);
        assertNull(view);
    }

    @Test
    public void testFloatingActionButton() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) readNoteActivity.findViewById(R.id.fab);
        fab.performClick();
        Intent intent = new Intent(readNoteActivity, CreateNote.class);
        assertEquals(CreateNote.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
