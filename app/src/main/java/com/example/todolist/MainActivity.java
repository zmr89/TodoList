package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNote;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;

    private NotesDatabase notesDatabase;

    private Handler handler = new Handler(Looper.getMainLooper());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesDatabase = NotesDatabase.getNotesDatabase(getApplication());


        initViews();

        notesAdapter = new NotesAdapter();
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                //ToDO
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        notesDatabase.notesDAO().remove(note.getId());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showNotes();
                            }
                        });

                    }
                });
                thread.start();

            }
        });

        itemTouchHelper.attachToRecyclerView(notesRecyclerView);

        addNote();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showNotes();
    }

    private void initViews(){
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

    private void showNotes(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> noteList = notesDatabase.notesDAO().getNotes();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notesAdapter.setNotes(noteList);
                    }
                });
            }
        });
        thread.start();
    }

    private void addNote() {
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}