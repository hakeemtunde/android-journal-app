package com.andela.gudacity.scholar.journalapp;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;

public class JournalAddActivity extends AppCompatActivity {

    private final static String TAG = JournalAddActivity.class.getSimpleName();

    private EditText mNoteEditText;
    private EditText mTagEditText;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize controls
       mNoteEditText = (EditText) findViewById(R.id.et_note);
       mTagEditText = (EditText) findViewById(R.id.et_tag);
       mSaveButton = (Button) findViewById(R.id.btn_save);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JournalAddActivity.this, "you click me!", Toast.LENGTH_SHORT).show();
            }
        });



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



    private void saveJournal(String note, String tag) {
        Journal journal = new Journal(note, tag, "28-07-2018");
    }
}
