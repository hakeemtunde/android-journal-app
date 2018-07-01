package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
@Entity(tableName = "journals")
public class Journal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tag;
    private String note;
    private String email;
    private Date date;

    //for firebase
    @Ignore
    public Journal() {
    }

    public Journal(int id, String tag, String note, String email, Date date) {
        this.id = id;
        this.tag = tag;
        this.note = note;
        this.email = email;
        this.date = date;
    }

    @Ignore
    public Journal(String tag, String note, String email, Date date) {
        this.tag = tag;
        this.note = note;
        this.email = email;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    @Exclude
    public String toString() {
        return "Journal{" +
                "tag='" + tag + '\'' +
                ", note='" + note + '\'' +
                ", timestamp='" + date + '\'' +
                '}';
    }
}
