package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;

import java.util.List;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM journals")
    List<Journal> loadAll();

    @Query("SELECT * FROM journals WHERE email = :email ORDER BY date")
    List<Journal> loadUserJournals(String email);

    @Query("SELECT * FROM journals WHERE id = :id")
    Journal findJournalById(int id);

    @Insert
    void insertJournal(Journal journal);

    @Update
    void updateJournal(Journal journal);

    @Delete
    void deleteJournal(Journal journal);
}
