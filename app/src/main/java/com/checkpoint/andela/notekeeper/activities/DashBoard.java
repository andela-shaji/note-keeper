package com.checkpoint.andela.notekeeper.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.helpers.ActivityLauncher;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initialize();
    }

    private void initialize(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NoteKeeper");
        ImageButton createEventBtn = (ImageButton) findViewById(R.id.dashboard_create_note);
        createEventBtn.setOnClickListener(this);
        ImageButton listEventButton = (ImageButton) findViewById(R.id.dashboard_list_notes);
        listEventButton.setOnClickListener(this);
        ImageButton settingsEventButton = (ImageButton) findViewById(R.id.dashboard_settings_note);
        settingsEventButton.setOnClickListener(this);
        ImageButton trashEventButton = (ImageButton) findViewById(R.id.dashboard_trash_note);
        trashEventButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Class activitySwitch = null;
        switch (v.getId()) {
            case R.id.dashboard_create_note:
                activitySwitch = CreateNote.class;
                break;
            case R.id.dashboard_list_notes:
                activitySwitch = ListNotes.class;
                break;
            case R.id.dashboard_trash_note:
                activitySwitch = Trash.class;
                break;
            case R.id.dashboard_settings_note:
                activitySwitch = AppSettings.class;
                break;
        }
        ActivityLauncher.runIntent(this, activitySwitch);
        finish();
    }

    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}
