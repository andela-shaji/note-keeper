package com.checkpoint.andela.notekeeper.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.adapter.ListNoteAdapter;
import com.checkpoint.andela.notekeeper.helpers.ActivityLauncher;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import java.util.ArrayList;

public class Trash extends ListNotes {

    private Toolbar toolbar;
    private ArrayList<NoteModel> noteModelArrayList;
    private ListNoteAdapter listNoteAdapter;
    private ListView listView;
    private ActionMode mActionMode;
    private int notePosition;
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
        listView.setItemsCanFocus(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list);
        fab.setVisibility(fab.INVISIBLE);

        listNotes(noteModelArrayList, "yes");
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
    public void setEmptyView(ArrayList<NoteModel> notes) {
        if (notes.size() < 1) {
            View view = findViewById(R.id.frame_empty);
            TextView textView = (TextView) findViewById(R.id.empty_layout);
            textView.setText("Trash is empty");
            ImageView imageView = (ImageView) findViewById(R.id.empty_image);
            imageView.setImageResource(R.drawable.empty_trash);
            view.setVisibility(view.VISIBLE);
        }
        super.setEmptyView(notes);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
            ActivityLauncher.runIntent(this, DashBoard.class);
            finish();
        } else if (id == R.id.nav_notes){
            ActivityLauncher.runIntent(this, ListNotes.class);
            finish();
        }  else if (id == R.id.nav_settings) {
            ActivityLauncher.runIntent(this, AppSettings.class);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.list_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        SparseBooleanArray checked = listView.getCheckedItemPositions();
        boolean hasCheckedElement = false;

        for (int i = 0 ; i < checked.size() && ! hasCheckedElement ; i++) {
            hasCheckedElement = checked.valueAt(i);
        }

        if (hasCheckedElement) {
            if (mActionMode == null) {
                notePosition = position;
                mActionMode = startActionMode(mActionModeCallback);
            }
        } else {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        }

    }

    private class DeleteNoteDialogue extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder deleteNote = new AlertDialog.Builder(getActivity());
            deleteNote.setMessage("Are you sure you want to delete all your notes?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeAllNotes("yes");
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
            return deleteNote.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trash, menu);
        final MenuItem item = menu.findItem(R.id.action_search_trash);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.trash) {
            if (noteModelArrayList.size() > 0) {
                DeleteNoteDialogue td = new DeleteNoteDialogue();
                FragmentManager fm = getSupportFragmentManager();
                td.show(fm, "Empty Trash");
                td.setRetainInstance(true);
                listNoteAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
            inflater.inflate(R.menu.menu_trash_popup, menu);
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
                case R.id.restore_pop:
                    moveNote(noteModelArrayList, listNoteAdapter, "no", notePosition);
                    Toast.makeText(Trash.this, "Note restored", Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;
                case R.id.trash_popup:
                    removeOneNote(noteModelArrayList, listNoteAdapter, notePosition);
                    Toast.makeText(Trash.this, "Note deleted", Toast.LENGTH_LONG).show();
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
        ActivityLauncher.runIntent(this, DashBoard.class);
        finish();
    }
}
