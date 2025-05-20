package com.example.wordroomsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements WordListAdapter.OnItemClickListener {
    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_WORD_ACTIVITY_REQUEST_CODE = 2;
    private WordListAdapter adapter;
    private String wordToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new WordListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, words -> {
            adapter.setWords(words);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public void onEditClick(Word word) {
        wordToEdit = word.getWord();
        Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
        intent.putExtra(NewWordActivity.EXTRA_REPLY, wordToEdit);
        startActivityForResult(intent, EDIT_WORD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onDeleteClick(Word word) {
        mWordViewModel.deleteWord(word);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE || requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK) {
            String newWordStr = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            if (requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE) {
                // Delete old word and insert new word
                mWordViewModel.deleteWord(new Word(wordToEdit));
            }
            Word newWord = new Word(newWordStr);
            mWordViewModel.insert(newWord);
        }
    }
}
