package com.checkpoint.andela.notekeeper.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.adapter.ListNoteAdapter;
import com.checkpoint.andela.notekeeper.helpers.ActivityLauncher;
import com.checkpoint.andela.notekeeper.model.NoteModel;

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
    public void onBackPressed() {
        ActivityLauncher.runIntent(this, DashBoard.class);
        finish();
    }
}
