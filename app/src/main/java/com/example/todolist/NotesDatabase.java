package com.example.todolist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//exportSchema по умолчанию true и его можно не указывать, если вдруг будут ошибки при создании БД может помощь укзание exportSchema = false
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instanceNotesDatabase = null;
    private static final String NAME_DB = "notes.db";

//allowMainThreadQueries() позволяет запустить баззу данных в основном потоке для тестирования приложения. В реальных приложениях забросы к базе данных нужно выполнять в фоне что не заблокировать основной поток, иначе все зависнет пока идет запрос
    public static NotesDatabase getNotesDatabase(Application application){
        if (instanceNotesDatabase == null){
            instanceNotesDatabase = Room.databaseBuilder(
                    application,
                    NotesDatabase.class,
                    NAME_DB
            ).build();
        }
        return instanceNotesDatabase;
    }

    public abstract NotesDAO notesDAO();

}
