package com.example.adria.myappmvp.data.local;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class NoteRepository {
    private NoteDao mNoteDao;
    private TaskDao mTaskDao;
    private static NoteRepository INSTANCE = null;

    private NoteRepository(Context context) {
        LocalAppDatabase database = LocalAppDatabase.getDatabase(context);
        mNoteDao = database.noteDao();
        mTaskDao = database.taskDao();
    }

    public List<Note> getNotesList() {
        return mNoteDao.getNotes();
    }

    public String insertNote(Note note) {
        if (note.getPosition() < 0)
            note.setPosition(getNoteCount() + 1);
        mNoteDao.insertNote(note);
        return note.getId();
    }

    public void deleteAllNotes() {
        mNoteDao.deleteAllNotes();
    }

    public Note getNoteFromId(String id) {
        return mNoteDao.getNoteFromId(id);
    }

    public void updateNote(Note note) {
        mNoteDao.updateNote(note);
    }

    public void deleteNote(Note note) {
        mNoteDao.deleteNote(note);
    }

    public int getNoteCount() {
        return mNoteDao.getNoteCount();
    }

    public static NoteRepository getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(context);
        }
        return INSTANCE;
    }

    //TASKS

    public ArrayList<Task> getNoteTasks(String noteId) {
        return (ArrayList<Task>) mNoteDao.getNoteTasks(noteId);
    }

    public void insertTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void insertTasks(ArrayList<Task> tasks) {
        mTaskDao.insertTasks(tasks);
    }

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    public void updateTask(Task task) {
        mTaskDao.updateTask(task);
    }

}

