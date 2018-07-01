package com.andela.gudacity.scholar.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository.JournalRepo;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.AppExecutors;

import java.util.Date;

import static com.andela.gudacity.scholar.journalapp.DetailActivity.EXTRA_JOURNAL_ID;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mNoteEditText;
    private EditText mTagEditText;
    private Button mOfflineUpdateButton;

    private JournalRepo mJournalRepo;
    private Journal mJournal;

    private int mJournalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNoteEditText = (EditText)findViewById(R.id.et_update_note);
        mTagEditText = (EditText)findViewById(R.id.et_update_tag);
        mOfflineUpdateButton = (Button)findViewById(R.id.btn_offline_update);

        //initialize repo
        mJournalRepo = new JournalRepo(this);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_JOURNAL_ID)) {

            mJournalId = intent.getIntExtra(EXTRA_JOURNAL_ID, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Journal journal = mJournalRepo.findJournalById(mJournalId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateUI(journal);
                        }
                    });
                }
            });
        }

        mOfflineUpdateButton.setOnClickListener(this);

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

        mJournal.setNote(mNoteEditText.getText().toString());
        mJournal.setTag(mTagEditText.getText().toString());
        mJournal.setDate(new Date());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mJournalRepo.update(mJournal);
            }
        });

        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT)
                .show();

        //start journal activity
        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }

    private void populateUI(Journal journal) {
        if (journal == null) return;

        mJournal = journal;
        mNoteEditText.setText(journal.getNote());
        mTagEditText.setText(journal.getTag());
    }
}
