package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDAO notesDAO;
    private MutableLiveData<Boolean> isFinishSaveML = new MutableLiveData<>();
    private Boolean isFinishSave = false;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);

        notesDAO = NotesDatabase.getNotesDatabase(application).notesDAO();
    }

    public void saveNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDAO.add(note);
//setValue() можно вызывать только из главного потока, а postValue() можно вызывать из любого потока. Используем postValue() т.к. мы запускаем метод из фонового потока thread
                isFinishSave = true;
                isFinishSaveML.postValue(isFinishSave);
            }
        });
        thread.start();

    }

    public LiveData<Boolean> saveFinish(){
        return isFinishSaveML;
    }
}
