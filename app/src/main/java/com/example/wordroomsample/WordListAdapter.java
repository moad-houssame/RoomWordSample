package com.example.wordroomsample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private List<Word> mWords;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onEditClick(Word word);
        void onDeleteClick(Word word);
    }

    public WordListAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_list_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
            holder.wordItemView.setOnClickListener(v -> {
                showOptionsDialog(current);
            });
        } else {
            holder.wordItemView.setText("No Word");
        }
    }

    private void showOptionsDialog(Word word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(word.getWord());
        String[] options = {"Edit", "Delete"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                listener.onEditClick(word);
            } else if (which == 1) {
                listener.onDeleteClick(word);
            }
        });
        builder.show();
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.text_word);
        }
    }
}
