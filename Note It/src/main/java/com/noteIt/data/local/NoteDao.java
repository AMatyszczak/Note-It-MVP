package com.noteIt.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */
@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note WHERE archived = 0 ORDER BY position ASC")
    List<Note> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Query("SELECT * From Note WHERE id = :id")
    Note getNoteFromId(String id);

    @Query("DELETE FROM Note")
    void deleteAllNotes();

    @Query("SELECT COUNT() FROM Note")
    int getNoteCount();

    @Delete
    void deleteNotes(ArrayList<Note> notes);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Update
    void updateNotes(ArrayList<Note> notes);

    //Archived Notes

    @Query("SELECT * FROM Note WHERE archived = 1")
    List<Note> getArchivedNotes();


}
