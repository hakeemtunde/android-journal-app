package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.DateConverter;

@Database(entities = {Journal.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SQLiteConn extends RoomDatabase {

    private static final String TAG = SQLiteConn.class.getSimpleName();
    private static final String DBNAME = "journaldb";
    private static SQLiteConn sInstance;

    public static SQLiteConn getInstance(Context context) {

        if (sInstance == null) {
            synchronized (new Object()) {
                Log.d(TAG, "Creating database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SQLiteConn.class, DBNAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract JournalDao journalDao();
}
