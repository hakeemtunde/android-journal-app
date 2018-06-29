package com.andela.gudacity.scholar.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JournalActivity extends AppCompatActivity
        implements JournalAdapter.ListItemClickListener {

    private static final String TAG = JournalActivity.class.getSimpleName();

    private JournalAdapter mJournalAdapter;
    private RecyclerView mJournalRecycleView;
    private List<Journal> mJournalList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDbRef;
    private FirebaseDatabase mFireDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        mJournalList = new ArrayList();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //setUpAuthListener();

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFireDatabase = FirebaseDatabase.getInstance();
        mDbRef = mFireDatabase.getReference("users-journals"); //writing

        //persist();
        retrieveJournal();

        mJournalRecycleView = (RecyclerView) findViewById(R.id.rv_journals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mJournalRecycleView.setLayoutManager(layoutManager);
        mJournalRecycleView.setHasFixedSize(true);

        mJournalAdapter = new JournalAdapter(mJournalList, this);
        mJournalRecycleView.setAdapter(mJournalAdapter);

        mJournalAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_journal, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAuthListener.onAuthStateChanged(mAuth);
        if (mUser == null) {
           launchMainActivity();
           return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemId = item.getItemId();

        if (menuItemId == R.id.action_add) {
            launchAddIntentActivity();
            return true;
        }

        if (menuItemId == R.id.action_logout) {
            mAuth.signOut();
            launchMainActivity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int journalPosition) {
        Journal journal = mJournalList.get(journalPosition);
        Log.d(this.getClass().getSimpleName(), journal.toString());

    }

    private void launchAddIntentActivity() {
        Intent intent = new Intent(this, JournalAddActivity.class);
        startActivity(intent);

    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void persist() {

        String key = mDbRef.child(mUser.getUid()).push().getKey();

        Journal nj = new Journal(mUser.getEmail(),
                "at school i was able to see my friend ", String.valueOf(new Date().getTime()));
        mDbRef.child(mUser.getUid()).child(key).setValue(nj);

        nj = new Journal(mUser.getEmail(), "market was boring", "12-12-2019");
        key = mDbRef.child(mUser.getUid()).push().getKey();
        mDbRef.child(mUser.getUid()).child(key).setValue(nj);

    }

    private void retrieveJournal() {


        mDbRef = mFireDatabase.getReference("users-journals").child(mUser.getUid());

        Log.w(TAG,mUser.getUid());

        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Journal journal = snapshot.getValue(Journal.class);
                    mJournalList.add(journal);

                    Log.w(TAG, journal.toString());
                    mJournalList.add(new Journal("---", "-----", "---33"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void setUpAuthListener() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    launchMainActivity();
                }

            }
        };
    }
}
