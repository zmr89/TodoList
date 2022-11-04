package com.example.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDAO notesDAO;
    private MutableLiveData<Boolean> isFinishSaveML = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Boolean isFinishSave = false;


    public AddNoteViewModel(@NonNull Application application) {
        super(application);

        notesDAO = NotesDatabase.getNotesDatabase(application).notesDAO();
    }

    public void saveNote(Note note){
        Disposable disposable = notesDAO.add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
//setValue() можно вызывать только из главного потока, а postValue() можно вызывать из главного и фонового, но не всегда есть гарантия его успешного выполнения.
                        Log.d("AddNoteViewModel", "subscribe");
                        isFinishSave = true;
                        isFinishSaveML.setValue(isFinishSave);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("AddNoteViewModel", "SaveNote error");
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData<Boolean> saveFinish(){
        return isFinishSaveML;
    }




}


