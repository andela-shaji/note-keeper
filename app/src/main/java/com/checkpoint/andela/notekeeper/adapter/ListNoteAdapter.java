package com.checkpoint.andela.notekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.checkpoint.andela.notekeeper.R;
import com.checkpoint.andela.notekeeper.model.NoteModel;

import java.util.ArrayList;

/**
 * Created by suadahaji.
 */
public class ListNoteAdapter extends ArrayAdapter<NoteModel> implements PopupMenu.OnMenuItemClickListener {

    public ListNoteAdapter(Context context, ArrayList<NoteModel> noteModels) {
        super(context, 0, noteModels);
    }

    static class LayoutHandler {
        TextView note_title, note_content, note_date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NoteModel noteModel = getItem(position);
        View row = convertView;
        LayoutHandler layoutHandler;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.notes_row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.note_title = (TextView) row.findViewById(R.id.row_note_title);
            layoutHandler.note_content = (TextView) row.findViewById(R.id.row_note_content);
            layoutHandler.note_date = (TextView) row.findViewById(R.id.row_note_date);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        layoutHandler.note_title.setText(noteModel.getNote_title());
        layoutHandler.note_content.setText(noteModel.getNote_content());
        layoutHandler.note_date.setText(noteModel.getNote_date());

        return row;
        }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
