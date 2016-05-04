package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.adapter.ListNoteAdapter;
import com.checkpoint.andela.notekeeper.helpers.ActivityLauncher;
import com.checkpoint.andela.notekeeper.helpers.NoteView;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import java.util.ArrayList;

public class ListNotes extends NoteView {

    private ArrayList<NoteModel> noteModelArrayList;
    private int notePosition;
    private ListView listView;
    private ListNoteAdapter listNoteAdapter;
    private Toolbar toolbar;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notes);
        initialize();
    }

    public void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpNavigationDrawer();
        noteModelArrayList = new ArrayList<>();
        listNoteAdapter = new ListNoteAdapter(this, noteModelArrayList);
        listView = (ListView) findViewById(R.id.listview_note);
        listView.setAdapter(listNoteAdapter);
        listView.setOnItemClickListener(this);
        onLongClick(listView);

        listNotes(noteModelArrayList, "no");
        createNote();

    }

    private void onLongClick(ListView listView) {
        listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                notePosition = position;
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_notes_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, AppSettings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final ArrayList<NoteModel> noteFilter = filterSearch(noteModelArrayList, query);
        listNoteAdapter = new ListNoteAdapter(this, noteFilter);
        listView.setAdapter(listNoteAdapter);
        listNoteAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NoteModel noteModel = listNoteAdapter.getItem(position);
        ActivityLauncher.eventIntent(this, ReadNote.class, noteModel);
    }

    private void setUpNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.list_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
            ActivityLauncher.runIntent(this, DashBoard.class);
            finish();
        } else if (id == R.id.nav_trash_delete) {
            ActivityLauncher.runIntent(this, Trash.class);
            finish();
        }  else if (id == R.id.nav_settings) {
            ActivityLauncher.runIntent(this, AppSettings.class);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.list_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CONTEXTUAL ACTION CALLBACK
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        /**
         * Called when the action mode is created; startActionMode() was called
         */

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Options");
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.create_note_menu, menu);
            return true;
        }

        /**
         * Called each time the action mode is shown.
         */

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }


        /**
         * Called when the user selects a contextual menu item
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_edit:
                    editNote();
                    mode.finish();
                    return true;
                case R.id.nav_share:
                    shareNote();
                    mode.finish();
                    return true;
                case R.id.nav_trash:
                    moveNote(noteModelArrayList, listNoteAdapter, "yes", notePosition);
                    Toast.makeText(ListNotes.this, "Note moved to trash", Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Called when the user exits the action mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    public void onBackPressed() {
        ActivityLauncher.runIntent(ListNotes.this, DashBoard.class);
        finish();
    }
}
