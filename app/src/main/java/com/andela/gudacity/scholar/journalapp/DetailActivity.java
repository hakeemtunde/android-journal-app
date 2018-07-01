package com.andela.gudacity.scholar.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository.JournalRepo;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.repository.SQLiteConn;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.AppExecutors;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_JOURNAL_ID = "journalId";

    private TextView mNoteTextView;
    private TextView mTagTexView;
    private Button mEditButton;

    private int mJournalId;

    private JournalRepo mJournalRepo;
    private Journal mJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNoteTextView = (TextView)findViewById(R.id.tv_detail_note);
        mTagTexView = (TextView)findViewById(R.id.tv_detail_tag);
        mEditButton = (Button)findViewById(R.id.btn_detail_edit);

        mNoteTextView.setMovementMethod(new ScrollingMovementMethod());

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

        mEditButton.setOnClickListener(this);


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
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra(DetailActivity.EXTRA_JOURNAL_ID, mJournal.getId());
        startActivity(intent);
    }

    private void populateUI(Journal journal) {
        if (journal == null) return;

        mJournal = journal;
        mNoteTextView.setText(journal.getNote());
        mTagTexView.setText(journal.getTag());
    }
}
