package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.saveFinish().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean saveFinish) {
                if (saveFinish){
                    finish();
                }

            }
        });

        initViews();

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
        if (noteText.isEmpty()) {
            Toast.makeText(this, "Введите заметку", Toast.LENGTH_SHORT).show();
        } else {
            Note newNote = new Note(noteText, priority);
            addNoteViewModel.saveNote(newNote);
        }
    }

    private int getPriority(){
        int priority;
        if (lowRadioButton.isChecked()) {
            Log.d("lowRadioButton",String.valueOf(lowRadioButton.isChecked()));
            priority = 0;
        }else if (mediumRadioButton.isChecked()) {
            Log.d("mediumRadioButton",String.valueOf(mediumRadioButton.isChecked()));
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