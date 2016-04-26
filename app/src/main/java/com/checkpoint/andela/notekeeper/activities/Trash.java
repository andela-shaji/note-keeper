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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Trash extends ListNotes {

    private Toolbar toolbar;
    private ArrayList<NoteModel> noteModelArrayList;
    private ListNoteAdapter listNoteAdapter;
    ListView listView;

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
    }

    @Override
    public void setEmptyView(ArrayList<NoteModel> notes) {
        if (notes.size() < 1) {
            View view = findViewById(R.id.frame_empty);
            TextView textView = (TextView) findViewById(R.id.empty_layout);
            textView.setText("Trash is empty");
            ImageView imageView = (ImageView) findViewById(R.id.empty_image);
            imageView.setImageResource(R.drawable.empty_trash);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list);
            fab.setVisibility(fab.INVISIBLE);
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
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.list_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.menu_trash_popup);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.restore_pop:
                        moveNote(noteModelArrayList, listNoteAdapter, "no", position);
                        Toast.makeText(Trash.this, "Note restored", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.trash_popup:
                        removeOneNote(noteModelArrayList, listNoteAdapter, position);
                        Toast.makeText(Trash.this, "Note deleted", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        if (showIcons(popup)) {
            return;
        }
        popup.show();
    }

    private boolean showIcons(PopupMenu popup) {
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            Log.w("POPUP", "error forcing menu icons to show", e);
            popup.show();
            return true;
        }
        return false;
    }

    public class DeleteNoteDialogue extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder deleteNote = new AlertDialog.Builder(getActivity());
            deleteNote.setMessage("Are you sure you want to delete all the notes in the trash?")
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
       /* final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);*/
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
            DeleteNoteDialogue td = new DeleteNoteDialogue();
            FragmentManager fm = getSupportFragmentManager();
            td.show(fm, "Empty Trash");
            listNoteAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        ActivityLauncher.runIntent(this, DashBoard.class);
        finish();
    }
}
