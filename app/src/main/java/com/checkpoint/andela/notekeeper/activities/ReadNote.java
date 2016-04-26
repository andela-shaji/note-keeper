package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Scroller;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.helpers.ActivityLauncher;
import com.checkpoint.andela.notekeeper.helpers.EditTextLined;
import com.checkpoint.andela.notekeeper.model.NoteModel;

public class ReadNote extends AppCompatActivity {
    private NoteModel noteModel;
    private EditText note_title;
    private EditTextLined note_content;

    private long note_id;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        initialize();
    }

    private void initialize() {
        note_title = (EditText) findViewById(R.id.read_note_title);
        note_content = (EditTextLined) findViewById(R.id.read_note_content);
        note_content.setScroller(new Scroller(ReadNote.this));
        note_content.setMovementMethod(new ScrollingMovementMethod());
        note_content.setVerticalScrollBarEnabled(true);

        manageToolBar();
        setView();
        editNote();
    }

    private void manageToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // If your minSdkVersion is below 11 use:
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // If your minSdkVersion is 11 or higher use:
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadNote.this, ListNotes.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void setView(){
        Intent intent = getIntent();
        if (intent.getParcelableExtra("UPDATE") != null) {
            noteModel = intent.getParcelableExtra("UPDATE");
            note_title.setText(noteModel.getNote_title());
            note_title.setKeyListener(null);
            note_content.setText(noteModel.getNote_content());
            note_content.setKeyListener(null);
            this.note_id = noteModel.getNote_id();
            this.date = noteModel.getNote_date();
        }
    }

    private void editNote() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLauncher.eventIntent(ReadNote.this, CreateNote.class, noteModel);
            }
        });

    }

}
