package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository;

import android.util.Log;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseRepo {

    private static final String TAG = FirebaseRepo.class.getSimpleName();

    private  FirebaseDatabase mFireDatabase;
    private  DatabaseReference mDbRef;
    private FirebaseUser mUser;

    public FirebaseRepo(FirebaseUser user) {
        mFireDatabase = FirebaseDatabase.getInstance();
        mDbRef = mFireDatabase.getReference("users-journals"); //writing
        mUser = user;
    }

    //save to firebase
    //this works
    public void persistToFirebase(Journal journal) {
        String key = mDbRef.child(mUser.getUid()).push().getKey();
        mDbRef.child(mUser.getUid()).child(key).setValue(journal);
    }

    // on calling this method is get Permission denied
    // but check the log it dump the data
    public List<Journal> retrieveJournalFromFirebase() {

        mDbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://journal-app-1529882911671.firebaseio.com/users-journals/"+mUser.getUid());

//        mDbRef = mFireDatabase.getReference("users-journals/"+mUser.getUid());

        Log.w(TAG,mUser.getUid());

        final List<Journal> lists =  new ArrayList<>();

        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Journal journal = snapshot.getValue(Journal.class);
                    lists.add(journal);

                    Log.e(TAG, journal.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return lists;
    }
}

