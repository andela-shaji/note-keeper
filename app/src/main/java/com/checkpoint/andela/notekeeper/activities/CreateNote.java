package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.helpers.EditTextLined;
import com.checkpoint.andela.notekeeper.helpers.NoteDbHelper;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import static java.lang.Integer.parseInt;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CreateNote extends AppCompatActivity {

    private NoteModel note;
    private SQLiteDatabase database;
    private NoteDbHelper noteDbHelper;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        manageToolbar();
        initialize();
        autoSave();
    }

    public void manageToolbar() {
        toolbar = (Toolbar) findViewById(R.id.create_note_toolbar);
        setSupportActionBar(toolbar);
        setTitle("New Note");
        if (getSupportActionBar() != null) {
            // This method will make the icon and title pressable
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // This method controls whether to show the Activity icon or not
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initialize() {

        noteDbHelper = new NoteDbHelper(this);
        database = noteDbHelper.getWritableDatabase();
        note = new NoteModel();

        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
                Intent intent = new Intent(CreateNote.this, DashBoard.class);
                finish();
                startActivity(intent);
            }
        });
        updateNotes();
    }

    public void createNote() {
        EditText noteTitle = (EditText) findViewById(R.id.note_title);
        EditTextLined noteContent = (EditTextLined) findViewById(R.id.note_content);

        String note_title = noteTitle.getText().toString().trim();
        String note_content = noteContent.getText().toString().trim();

        if ((note_title.matches("")) && note_content.matches("")) {
            return;
        } else if (note_title.matches("") && note_content.contains(" ")) {
            note.setNote_title(note_content.substring(0, note_content.indexOf(" ")));
        } else {
            note.setNote_title(note_title);
        }
        note.setNote_content(note_content);
        insertNote(note);
    }

    public boolean insertNote(NoteModel noteModel) {
        cupboard().withDatabase(database).put(noteModel);
        return true;
    }

    public void updateNotes() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            note = intent.getParcelableExtra("UPDATE");
            this.setTitle("NoteKeeper");
            EditText titleNote = (EditText) findViewById(R.id.note_title);
            EditText contentNote = (EditText) findViewById(R.id.note_content);
            titleNote.setText(note.getNote_title());
            contentNote.setText(note.getNote_content());

        }
    }

    private int getUserTime() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String downloadType = SP.getString("downloadType", "2");
        return parseInt(downloadType);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            createNote();
            handler.postDelayed(this, 1000 * getUserTime());
        }
    };

    public void autoSave() {
        final EditText title = (EditText) findViewById(R.id.note_title);
        final EditText content = (EditText) findViewById(R.id.note_content);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (title.hasFocus() || content.hasFocus()) {
                    runnable.run();
                } else {
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

}