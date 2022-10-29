package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NotesDatabase notesDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        notesDatabase = NotesDatabase.getNotesDatabase(application);
    }

    public LiveData<List<Note>> getNotes(){
        return notesDatabase.notesDAO().getNotes();
    }

    public void removeNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDatabase.notesDAO().remove(note.getId());
            }
        });
        thread.start();
    }


}
