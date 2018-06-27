package com.andela.gudacity.scholar.journalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickItemListener;

    private List<Journal> mJournalList;

    public JournalAdapter(List<Journal> journals, ListItemClickListener listener) {
        mJournalList = journals;
        mOnClickItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_list_item, parent, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Journal journal = mJournalList.get(position);
        holder.bind(journal);
    }

    @Override
    public int getItemCount() {
        return mJournalList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int itemPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTextViewTag;
        private TextView mTextViewShortNote;
        private TextView mTextViewTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewTag = (TextView) itemView.findViewById(R.id.tv_tag);
            mTextViewShortNote = (TextView) itemView.findViewById(R.id.tv_short_note);
            mTextViewTimestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);

            itemView.setOnClickListener(this);
        }

        void bind(Journal journal) {
            mTextViewTag.setText(journal.getTag());
            mTextViewShortNote.setText(journal.getNote());
            mTextViewTimestamp.setText(journal.getTimestamp());
        }

        @Override
        public void onClick(View view) {

            int clickedJournalPosition = getAdapterPosition();
            mOnClickItemListener.onListItemClick(clickedJournalPosition);
        }
    }
}
