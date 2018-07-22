package com.noteIt.data.local;

import android.content.Context;
import android.util.Log;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.lang.reflect.Array;
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

    public void updateNotes(ArrayList<Note> notes) { mNoteDao.updateNotes(notes); }

    public void deleteNote(Note note) {

        mNoteDao.deleteNote(note);
        updateNotePositionOnDelete(note);
    }

    public void deleteNotes(ArrayList<Note> notes) {
        mNoteDao.deleteNotes(notes);
        updateNotesPositionsOnDelete(notes);}

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
        return (ArrayList<Task>) mTaskDao.getNoteTasks(noteId);
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

    //Private

    private int updateNotesPositionsOnDelete(ArrayList<Note> deletedNoteList)
    {
        int editedNotesCount = 0;
        ArrayList<Note> noteList = (ArrayList<Note>)mNoteDao.getNotes();

        int positionDiff = 0;
        for(Note note: noteList)
        {
            for (Note deletedNote: deletedNoteList)
            {
                if( note.getPosition() > deletedNote.getPosition())
                    positionDiff++;
            }
            if(positionDiff!=0)
            {
                note.subtractPosition(positionDiff);
                mNoteDao.updateNote(note);
                editedNotesCount++;
            }
            positionDiff = 0;
        }

        return editedNotesCount;
    }

    private int updateNotePositionOnDelete(Note note)
    {
        int editedNotesCount = 0;
        ArrayList<Note> noteList = (ArrayList<Note>)mNoteDao.getNotes();

        int N = noteList.size();
        for(int i = note.getPosition()+1 ; i < N; i++)
        {
            noteList.get(i).subtractPosition(1);
            editedNotesCount++;
        }
        return editedNotesCount;
    }
}

