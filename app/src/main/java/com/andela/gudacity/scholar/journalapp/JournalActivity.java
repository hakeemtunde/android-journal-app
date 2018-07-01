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
import android.view.View;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository.JournalRepo;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.AppExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private List<Journal> mJournalList = new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDbRef;
    private FirebaseDatabase mFireDatabase;

    private JournalRepo mJournalRepo;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //sqlite connection
        mJournalRepo = new JournalRepo(getApplicationContext());

        //loadData();

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFireDatabase = FirebaseDatabase.getInstance();
        mDbRef = mFireDatabase.getReference("users-journals"); //writing

        //firebase
//        retrieveJournalFromFirebase();

        mJournalRecycleView = (RecyclerView) findViewById(R.id.rv_journals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mJournalRecycleView.setLayoutManager(layoutManager);
        mJournalRecycleView.setHasFixedSize(true);

        mJournalAdapter = new JournalAdapter(mJournalList, this);
        mJournalRecycleView.setAdapter(mJournalAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_journal, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
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

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_JOURNAL_ID, journal.getId());
        startActivity(intent);

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

    private void loadData() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                //execute off main thread
                final List<Journal> journals = mJournalRepo
                        .getUserList(mUser.getEmail());

                //execute on main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mJournalAdapter.setJournalList(journals);
                    }
                });


            }
        });
    }

    private void persistToFirebase() {

        Journal nj = new Journal();
        nj.setNote("Note1");
        nj.setTag("at school i was able to see my friend");
        nj.setDate(new Date());

        String key = mDbRef.child(mUser.getUid()).push().getKey();
        mDbRef.child(mUser.getUid()).child(key).setValue(nj);

        nj.setNote("Note2");
        nj.setTag("market was boring");
        nj.setDate(new Date());
        key = mDbRef.child(mUser.getUid()).push().getKey();
        mDbRef.child(mUser.getUid()).child(key).setValue(nj);

    }

    private void retrieveJournalFromFirebase() {

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

                    //Journal j = new Journal(journal.tag, journal.note, journal.timestamp);
                    //mJournalList.add(j);
                    lists.add(journal);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        Log.w(TAG, "----------------------------------------");

        for (Journal journal : lists) {
            Log.w(TAG, journal.getNote());
        }
    }

}
