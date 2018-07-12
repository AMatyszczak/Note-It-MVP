package com.example.adria.myappmvp.data.local;

import android.content.Context;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class NoteRepository
{
    private NoteDao mNoteDao;
    private TaskDao mTaskDao;
    private static NoteRepository INSTANCE = null;

    private NoteRepository(Context context)
    {
        LocalAppDatabase database = LocalAppDatabase.getDatabase(context);
        mNoteDao = database.noteDao();
        mTaskDao = database.taskDao();
    }

    public List<Note> getNotesList()
    {
        return mNoteDao.getNotes();
    }

    public void insertNote(Note note)
    {
        if(note.getPosition() < 0)
            note.setPosition(getNoteCount()+1);
        mNoteDao.insertNote(note);
    }

    public void deleteAllNotes()
    {
        mNoteDao.deleteAllNotes();
    }

    public Note getNoteFromId(String id) { return mNoteDao.getNoteFromId(id); }

    public void updateNote(Note note) { mNoteDao.updateNote(note); }

    public void deleteNote(Note note) { mNoteDao.deleteNote(note);}

    public int getNoteCount() { return mNoteDao.getNoteCount(); }

    public static NoteRepository getINSTANCE(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new NoteRepository(context);
        }
        return INSTANCE;
    }

    //TASKS

    public List<Task> getNoteTasks(int noteId) { return mNoteDao.getNoteTasks(noteId); }

    public void updateTask(Task task ) { mTaskDao.updateTask(task); }

    public void deleteTask(Task task) { mTaskDao.deleteTask(task); }

}

