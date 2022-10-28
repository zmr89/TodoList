package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayoutNotes;
    private FloatingActionButton buttonAddNote;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Random random = new Random();
        for (int i = 0; i < 20; i++){
            Note newNote = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(newNote);
        }

        showNotes();

        addNote();

    }



    private void initViews(){
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

    private void showNotes(){
        for (Note note : notes){
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearLayoutNotes,
                    false);
            TextView textView = view.findViewById(R.id.textViewNote);
            textView.setText(note.getText());
            int colorResId;
            switch (note.getPriority()){
                case 0 : colorResId = android.R.color.holo_green_light;
                break;
                case 1 : colorResId = android.R.color.holo_orange_light;
                    break;
                default: colorResId = android.R.color.holo_red_light;
            }
            int color = ContextCompat.getColor(this, colorResId);
            textView.setBackgroundColor(color);
            linearLayoutNotes.addView(view);
        }
    }

    private void addNote() {
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteActivity.newIntent(MainActivity.this);
            }
        });
    }
}