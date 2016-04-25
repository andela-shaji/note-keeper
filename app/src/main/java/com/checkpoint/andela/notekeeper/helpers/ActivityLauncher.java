package com.checkpoint.andela.notekeeper.helpers;

import android.content.Context;
import android.content.Intent;

import com.checkpoint.andela.notekeeper.model.NoteModel;

/**
 * Created by suadahaji.
 */
public class ActivityLauncher {
    public static void runIntent(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void eventIntent(Context context, Class<?> activityClass, NoteModel noteModel) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra("UPDATE", noteModel);
        context.startActivity(intent);
    }
}
