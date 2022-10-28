package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText noteEditText;
    private RadioGroup priorityRadioGroup;
    private RadioButton lowRadioButton;
    private RadioButton mediumRadioButton;
    private RadioButton highRadioButton;
    private Button saveButton;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();

        database = Database.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

    }

    private void initViews() {
        noteEditText=findViewById(R.id.noteEditText);
        priorityRadioGroup = findViewById(R.id.priorityRadioGroup);
        lowRadioButton=findViewById(R.id.lowRadioButton);
        mediumRadioButton=findViewById(R.id.mediumRadioButton);
        highRadioButton=findViewById(R.id.highRadioButton);
        saveButton=findViewById(R.id.saveButton);
    }

    private void saveNote() {
        String noteText = noteEditText.getText().toString().trim();
        int priority = getPriority();
        int id = database.getNotes().size();
        if (noteText.isEmpty()) {
            Toast.makeText(this, "Введите заметку", Toast.LENGTH_SHORT).show();
        } else {
            Note newNote = new Note(id, noteText, priority);
            database.addNote(newNote);
            finish();
        }
    }

    private int getPriority(){
        int priority;
        if (lowRadioButton.isChecked()) {
            priority = 0;
        }
        if (mediumRadioButton.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }


}