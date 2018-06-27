package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model;

public class Journal {

    private String mTag;
    private String mNote;
    private String mTimestamp;

    public Journal() {

    }

    public Journal(String mTag, String mNote, String mTimestamp) {
        this.mTag = mTag;
        this.mNote = mNote;
        this.mTimestamp = mTimestamp;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "mTag='" + mTag + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mTimestamp='" + mTimestamp + '\'' +
                '}';
    }
}
