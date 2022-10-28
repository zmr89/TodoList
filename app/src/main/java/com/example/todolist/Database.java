package com.example.todolist;

import java.util.ArrayList;
import java.util.Random;

public class Database {

    private static Database database = null;
    private ArrayList<Note> notes = new ArrayList<>();

    public static Database getInstance(){
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private Database() {
        Random random = new Random();
        for (int i = 0; i < 20_000; i++){
            Note newNote = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(newNote);
        }
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public void removeNote(int id){
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id){
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes(){
        return new ArrayList<>(notes);
    }

}
