package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Build;
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
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class TrashTest {
    private Trash trashActivity;
    private ArrayList<NoteModel> noteModels;
    private TextView emptyTextView;

    @Before
    public void setUp() throws Exception {
        trashActivity = Robolectric.buildActivity(Trash.class).create().get();
        noteModels = new ArrayList<>();
        emptyTextView = (TextView) trashActivity.findViewById(R.id.empty_layout);
    }

    @Test
    public void testOnCreate() throws Exception {

        View view = trashActivity.findViewById(R.id.list_drawer_layout);
        assertNotNull(view);
    }

    @Test
    public void testOnNavigationItemSelected() throws Exception {
        MenuItem menuItem1 = new RoboMenuItem(R.id.nav_dashboard);
        assertEquals(trashActivity.onNavigationItemSelected(menuItem1), true);
    }

    @Test
    public void testBackPressed() {
        Intent intent = new Intent(trashActivity, DashBoard.class);
        assertEquals(DashBoard.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testOnOptionsItemsSelected() throws Exception {
        MenuItem menuItem = new RoboMenuItem(R.id.trash);
        assertEquals(trashActivity.onOptionsItemSelected(menuItem), false);
    }

    @Test
    public void testSetEmptyView() {
        trashActivity.setEmptyView(noteModels);
        assertTrue(emptyTextView.getText().toString().equals("Trash is empty"));
    }

    @Test
    public void testOnQueryTextChange() {
        assertEquals(trashActivity.onQueryTextChange("Suada"), true);
    }


}