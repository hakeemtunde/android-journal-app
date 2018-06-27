package com.andela.gudacity.scholar.journalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;

import java.util.ArrayList;
import java.util.List;

public class JournalActivity extends AppCompatActivity
        implements JournalAdapter.ListItemClickListener {

    private JournalAdapter mJournalAdapter;
    private RecyclerView mJournalRecycleView;
    private List<Journal> mJournalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        mJournalRecycleView = (RecyclerView) findViewById(R.id.rv_journals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mJournalRecycleView.setLayoutManager(layoutManager);
        mJournalRecycleView.setHasFixedSize(true);

        mJournalList = new ArrayList();
        mJournalList.add(new Journal("school today", "at school i was able to see my friend ",
                "12-12-2018"));
        mJournalList.add(new Journal("market", "market was boring",
                "12-12-2018"));

        mJournalList.add(new Journal("car driving", "the driving test was excellent. i think i have to rush them",
                "12-12-2018"));

        mJournalList.add(new Journal("programming", "computer programming is mad",
                "12-12-2018"));

        mJournalAdapter = new JournalAdapter(mJournalList, this);
        mJournalRecycleView.setAdapter(mJournalAdapter);




    }

    @Override
    public void onListItemClick(int journalPosition) {
        Journal journal = mJournalList.get(journalPosition);
        Log.d(this.getClass().getSimpleName(), journal.toString());

    }
}
