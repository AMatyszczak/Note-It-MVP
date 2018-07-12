package com.example.adria.myappmvp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */
@Dao
public interface NoteDao
{
    @Query("SELECT * FROM Note ORDER BY position ASC")
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
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT t.* FROM task t, note n WHERE t.noteId = :noteId ")
    List<Task> getNoteTasks(int noteId);
}
