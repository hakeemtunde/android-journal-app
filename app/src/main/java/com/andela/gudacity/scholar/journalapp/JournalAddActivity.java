package com.andela.gudacity.scholar.journalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository.JournalRepo;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.AppExecutors;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class JournalAddActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final static String TAG = JournalAddActivity.class.getSimpleName();

    private EditText mNoteEditText;
    private EditText mTagEditText;
    private Button mSaveOfflineButton;
    private Button mSaveOnlineButton;
    private JournalRepo mJournalRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize dbconnection
        mJournalRepo = new JournalRepo(getApplicationContext());

        //initialize controls
       mNoteEditText = (EditText) findViewById(R.id.et_note);
       mTagEditText = (EditText) findViewById(R.id.et_tag);
       mSaveOfflineButton = (Button) findViewById(R.id.btn_offline_save);
       mSaveOnlineButton = (Button) findViewById(R.id.btn_online_firebase);

        mSaveOfflineButton.setOnClickListener(this);
        mSaveOnlineButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_offline_save:
                saveToSQLight();
                break;
            case R.id.btn_online_firebase:
                saveToFirebase();
                break;
        }

    }

    private void saveToSQLight() {
        String note = mNoteEditText.getText().toString();
        String tag = mTagEditText.getText().toString();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        final Journal journal = new Journal(tag, note, email, new Date());
        Log.d(JournalAddActivity.class.getSimpleName(), journal.toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mJournalRepo.save(journal);
                finish();
            }
        });


        Toast.makeText(this, "Journal was saved!", Toast.LENGTH_LONG)
                .show();

    }

    private void saveToFirebase() {

    }

}
