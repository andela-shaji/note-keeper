package com.checkpoint.andela.notekeeper.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.checkpoint.andela.notekeeper.BuildConfig;
import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class ListNotesTest {

    private ListNotes listNotesActivity;
    private ShadowActivity shadowActivity;
    private ArrayList<NoteModel> noteModelArrayList;
    private TextView emptyTextView;


    @Before
    public void setUp() throws Exception {
        listNotesActivity = Robolectric.setupActivity(ListNotes.class);
        noteModelArrayList = new ArrayList<>();
        shadowActivity = Shadows.shadowOf(listNotesActivity);
        emptyTextView = (TextView) listNotesActivity.findViewById(R.id.empty_layout);
    }

    @Test
    public void testOnCreateView() throws Exception {
        View view = listNotesActivity.findViewById(R.id.list_drawer_layout);
        assertNotNull(view);
    }

    @Test
    public void testListNote() throws Exception {
        listNotesActivity.listNotes(noteModelArrayList, "no");
        assertNotNull(noteModelArrayList);
    }

    @Test
    public void testOnFloatActionButtonClick() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) listNotesActivity.findViewById(R.id.fab_list);
        fab.performClick();

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(CreateNote.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testSetEmptyView() {
        listNotesActivity.setEmptyView(noteModelArrayList);
        assertTrue(emptyTextView.getText().toString().equals("Notes added appear here"));
    }

    @Test
    public void testOptionsMenuActions() {
        MenuItem menuItem = new RoboMenuItem(R.id.action_settings);
        assertNotNull(menuItem);
        listNotesActivity.onOptionsItemSelected(menuItem);
        Intent expectedIntent = new Intent(listNotesActivity, AppSettings.class);
        assertTrue(shadowActivity.getNextStartedActivity().equals(expectedIntent));
    }


    @Test
    public void testOnNavigationItemSelected() throws Exception {
        MenuItem menuItem1 = new RoboMenuItem(R.id.nav_dashboard);
        assertEquals(listNotesActivity.onNavigationItemSelected(menuItem1), true);
        MenuItem menuItem2 = new RoboMenuItem(R.id.nav_trash_delete);
        assertEquals(listNotesActivity.onNavigationItemSelected(menuItem2), true);
        MenuItem menuItem3 = new RoboMenuItem(R.id.nav_settings);
        assertEquals(listNotesActivity.onNavigationItemSelected(menuItem3), true);
    }
}