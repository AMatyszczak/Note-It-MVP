package com.example.adria.myappmvp.data.local;

import android.content.Context;

import com.example.adria.myappmvp.data.Note;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class NoteRepository
{
    private TaskDao mNoteDao;
    private static NoteRepository INSTANCE = null;

    private NoteRepository(Context context)
    {
        LocalAppDatabase database = LocalAppDatabase.getDatabase(context);
        mNoteDao = database.taskDao();
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


}

