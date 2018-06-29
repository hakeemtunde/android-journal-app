package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Journal {

    public String tag;
    public String note;
    public String timestamp;

    public Journal() {

    }

    public Journal(String mTag, String note, String mTimestamp) {
        this.tag = mTag;
        this.note = note;
        this.timestamp = mTimestamp;
    }

    @Override
    @Exclude
    public String toString() {
        return "Journal{" +
                "tag='" + tag + '\'' +
                ", note='" + note + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
