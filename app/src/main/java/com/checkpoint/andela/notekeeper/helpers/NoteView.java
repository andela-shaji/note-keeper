package com.checkpoint.andela.notekeeper.helpers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.activities.CreateNote;
import com.checkpoint.andela.notekeeper.activities.ListNotes;
import com.checkpoint.andela.notekeeper.activities.ReadNote;
import com.checkpoint.andela.notekeeper.adapter.ListNoteAdapter;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import java.util.ArrayList;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class NoteView extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private ArrayList<NoteModel> noteModelArrayList;
    private int notePosition;
    private ListView listView;
    private SQLiteDatabase sqLiteDatabase;
    private ListNoteAdapter listNoteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notes);
        initialize();


    }

    public void initialize() {

        noteModelArrayList = new ArrayList<>();
        listNoteAdapter = new ListNoteAdapter(this, noteModelArrayList);
        listView = (ListView) findViewById(R.id.listview_note);
        listView.setAdapter(listNoteAdapter);
    }
    public void createNote() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteView.this, CreateNote.class);
                startActivity(intent);
            }
        });
    }

    public void listNotes(ArrayList<NoteModel> noteModelArrayList, String condition) {
        NoteDbHelper noteDbHelper = new NoteDbHelper(this);
        QueryResultIterable<NoteModel> itr = null;
        sqLiteDatabase = noteDbHelper.getReadableDatabase();
        // Get the cursor for this query
        Cursor notes = cupboard().withDatabase(sqLiteDatabase).query(NoteModel.class).withSelection("note_trashed = ?", condition).getCursor();
        try {
            // Iterate notes
            itr = cupboard().withCursor(notes).iterate(NoteModel.class);
            for (NoteModel note : itr) {
                noteModelArrayList.add(0, note);
            } setEmptyView(noteModelArrayList);
        } finally {
            // close the cursor
            itr.close();
        }
    }

    public void setEmptyView(ArrayList<NoteModel> notes) {
        if (notes.size() < 1) {
            View view = findViewById(R.id.frame_empty);
            view.setVisibility(view.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NoteModel noteModel = listNoteAdapter.getItem(position);
        ActivityLauncher.eventIntent(this, ReadNote.class, noteModel);
    }


    /**
     * Enables the user to edit the note
     */

    public void editNote(){
        NoteModel edit_note = noteModelArrayList.get(notePosition);
        NoteModel noteModel = cupboard().withDatabase(sqLiteDatabase).get(NoteModel.class, edit_note.getNote_id());
        ActivityLauncher.eventIntent(NoteView.this, CreateNote.class, noteModel);
    }

    /**
     * The user is able to share the note via email
     */

    public void shareNote() {
        NoteModel noteModel = noteModelArrayList.get(notePosition);
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_SUBJECT, " " + noteModel.getNote_title());
        mail.putExtra(Intent.EXTRA_TEXT, noteModel.getNote_content());
        //This is needed to prompt email client only
        mail.setType("message/rfc822");
        startActivity(Intent.createChooser(mail, "Send note"));
    }

    public void moveNote(ArrayList<NoteModel> noteModels, ListNoteAdapter listNoteAdapter, String remove, int notePosition) {
        NoteModel noteModel = noteModels.get(notePosition);
        noteModels.remove(notePosition);
        NoteModel currentNote = cupboard().withDatabase(sqLiteDatabase).get(NoteModel.class, noteModel.getNote_id());
        currentNote.setNoteTrashed(remove);
        cupboard().withDatabase(sqLiteDatabase).put(currentNote);
        listNoteAdapter.notifyDataSetChanged();
        reload();
    }

    public void  removeOneNote(ArrayList<NoteModel> noteModels,ListNoteAdapter listNoteAdapter, int position ) {
        NoteModel noteModel = noteModels.get(position);
        cupboard().withDatabase(sqLiteDatabase).delete(NoteModel.class, noteModel.getNote_id());
        noteModels.remove(position);
        listNoteAdapter.notifyDataSetChanged();
        reload();
    }

    public void removeAllNotes(String remove) {
        cupboard().withDatabase(sqLiteDatabase).delete(NoteModel.class, "note_trashed = ?", remove);
        reload();
    }

    private void reload(){
        Intent intent = getIntent();
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public ArrayList<NoteModel> filterSearch(ArrayList<NoteModel> noteModelArrayList, String search){
        search = search.toLowerCase();
        final ArrayList<NoteModel> filteredSearch = new ArrayList<>();
        for (NoteModel noteModel: noteModelArrayList) {
            final String note_title = noteModel.getNote_title().toLowerCase();
            final String note_content = noteModel.getNote_content().toLowerCase();
            if (note_title.contains(search) | note_content.contains(search)) {
                filteredSearch.add(noteModel);
            }
        }
        return filteredSearch;

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
