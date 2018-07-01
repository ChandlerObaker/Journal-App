package com.chandlerobaker.alcchallenge.android.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandlerobaker.alcchallenge.android.journalapp.database.DiaryEntry;
import com.chandlerobaker.alcchallenge.android.journalapp.utilities.Utils;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    final private ListItemClickListener mOnItemClickListener;

    private final List<DiaryEntry> mDiaryEntries;
    private final Context context;

    public DiaryAdapter(Context context, ListItemClickListener mOnItemClickListener, List<DiaryEntry> mDiaryEntries) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.mDiaryEntries = mDiaryEntries;
        this.context = context;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.diary_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryEntry diaryEntry = (mDiaryEntries != null) ? mDiaryEntries.get(position) : null;
        if (diaryEntry != null)
            holder.bind(diaryEntry.getMood(), Utils.formatDate(diaryEntry.getDateEnreg()), diaryEntry.getContent());

    }

    @Override
    public int getItemCount() {
        return (mDiaryEntries == null ? 0 : mDiaryEntries.size());
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView thoughtDateView, thoughtView;
        final ImageView moodView;

        private DiaryViewHolder(View itemView) {
            super(itemView);
            thoughtDateView = (TextView) itemView.findViewById(R.id.tv_date);
            thoughtView = (TextView) itemView.findViewById(R.id.tv_thought);
            moodView = (ImageView) itemView.findViewById(R.id.image_mood);

            itemView.setOnClickListener(this);
        }

        private void bind(int mood, String date, String thought) {
            thoughtDateView.setText(date);
            thoughtView.setText(Utils.contractString(thought));
            switch (mood) {
                case 0:
                    moodView.setImageResource(R.drawable.emoji_happy);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_happy_diary));
                    break;
                case 1:
                    moodView.setImageResource(R.drawable.emoji_hungry);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_hungry_diary));
                    break;
                case 2:
                    moodView.setImageResource(R.drawable.emoji_love);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_love_diary));
                    break;
                case 3:
                    moodView.setImageResource(R.drawable.emoji_sad);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_sad_diary));
                    break;
                case 4:
                    moodView.setImageResource(R.drawable.emoji_sick);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_sick_diary));
                    break;
                case 5:
                    moodView.setImageResource(R.drawable.emoji_tired);
                    thoughtView.setBackground(context.getDrawable(R.drawable.rounded_tired_diary));
                    break;
            }
            //code for mood
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnItemClickListener.onListItemClick(clickedPosition);
        }
    }

}
