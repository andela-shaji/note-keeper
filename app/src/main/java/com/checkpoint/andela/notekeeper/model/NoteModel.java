package com.checkpoint.andela.notekeeper.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by suadahaji.
 */
public class NoteModel implements Parcelable {
    private Long _id; // for cupboard to serve as the index within the SQLite table.
    private String note_title;
    private String note_content;
    private String note_date;
    private String note_time;
    private String note_trashed = null;

    public NoteModel() {
        this.note_date = setNote_date();
        setNoteTrashed("no");
    }

    public Long getNote_id() {
        return _id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public String getNote_date() {
        return this.note_date;
    }

    public String setNote_date() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        return dateFormat.format(timestamp);
    }

    public String getNoteTrashed() {
        return note_trashed;
    }

    public void setNoteTrashed(String isTrashed) {
        this.note_trashed = isTrashed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * In the writeToParcel method write all the class attributes
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this._id);
        dest.writeString(this.note_title);
        dest.writeString(this.note_content);
        dest.writeString(this.note_date);
        dest.writeString(this.note_trashed);
    }

    /**
     * De-parcel the object(initialise the class attributes) in the constructor
     */
    public NoteModel(Parcel in) {
        this._id = in.readLong();
        this.note_title = in.readString();
        this.note_content = in.readString();
        this.note_date = in.readString();
        this.note_trashed = in.readString();
    }

    /**
     * In the Parcel.Creator read the parcel data
     */
    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel source) {
            return new NoteModel(source);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

}
