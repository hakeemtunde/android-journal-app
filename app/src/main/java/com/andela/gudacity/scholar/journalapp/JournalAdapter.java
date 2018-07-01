package com.andela.gudacity.scholar.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.model.Journal;
import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.DateUtil;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private static final String TAG = JournalAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickItemListener;

    private List<Journal> mJournalList;


    public interface ListItemClickListener {
        void onListItemClick(int itemPosition);
    }

    public JournalAdapter(List<Journal> journals, ListItemClickListener listener) {
        mJournalList = journals;
        mOnClickItemListener = listener;
    }

    public void setJournalList(List<Journal> journals) {
        mJournalList.clear();
        mJournalList.addAll(journals);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Journal journal = mJournalList.get(position);
        holder.bind(journal);

    }

    @Override
    public int getItemCount() {
        if (mJournalList.size() == 0 ) return 0;
        return mJournalList.size();
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
            mTextViewTimestamp.setText(DateUtil
                    .getDateFormat(journal.getDate()));
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Item Clicked!!");
            int clickedJournalPosition = getAdapterPosition();
            mOnClickItemListener.onListItemClick(clickedJournalPosition);
        }


    }
}
