package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository;

import android.content.Context;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;

import java.util.List;

public class JournalRepo {

    private SQLiteConn mDb;

    public JournalRepo(Context context) {
        mDb = SQLiteConn.getInstance(context);
    }

    public void save(Journal journal) {
        mDb.journalDao().insertJournal(journal);
    }

    public void update(Journal journal) {
        mDb.journalDao().updateJournal(journal);
    }

    public List<Journal> getAll() {
        return mDb.journalDao().loadAll();
    }

    public List<Journal> getUserList(String email) {
        return mDb.journalDao().loadUserJournals(email);
    }

    public Journal findJournalById(int id) {
        return mDb.journalDao().findJournalById(id);
    }
}
