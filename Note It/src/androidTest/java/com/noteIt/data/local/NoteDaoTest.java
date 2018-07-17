package com.noteIt.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.noteIt.data.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class NoteDaoTest
{

    private static final Note NOTE = new Note("Title", "Description", 1);

    LocalAppDatabase mAppDatabase;
    NoteDao mNoteDao;

    @Before
    public void init()
    {
        mAppDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), LocalAppDatabase.class).build();
        mNoteDao = mAppDatabase.noteDao();
    }

    @After
    public void closeDb()
    {
        mAppDatabase.close();
    }

    @Test
    public void insertNote()
    {
        mNoteDao.insertNote(NOTE);

        Note note = mNoteDao.getNoteFromId(NOTE.getId());

        checkNote(note, NOTE.getId(), NOTE.getTitle(), NOTE.getDescription(), NOTE.getPosition());

    }

    @Test
    public void insertNote_getNotes()
    {

        mNoteDao.insertNote(NOTE);
        List<Note> noteList = mNoteDao.getNotes();

        checkNote(noteList.get(0), NOTE.getId(), NOTE.getTitle(), NOTE.getDescription(),NOTE.getPosition());
//        assertThat(noteList.get(0), is(equalTo(NOTE)));
    }

    @Test
    public void insertNote_OnConflict()
    {
        mNoteDao.insertNote(NOTE);
        NOTE.setDescription("DESC");
        mNoteDao.insertNote(NOTE);
        Note note = mNoteDao.getNoteFromId(NOTE.getId());
        checkNote(note, NOTE.getId(), NOTE.getTitle(), NOTE.getDescription(), NOTE.getPosition());
    }

    @Test
    public void insertNote_getNoteById()
    {
        mNoteDao.insertNote(NOTE);

        Note note = mNoteDao.getNoteFromId(NOTE.getId());

        checkNote(note, NOTE.getId(), NOTE.getTitle(), NOTE.getDescription(),NOTE.getPosition());
        //assertThat(note, is(equalTo(NOTE)));
    }

    @Test
    public void insertNote_deleteAllNotes()
    {
        mNoteDao.insertNote(NOTE);

        mNoteDao.deleteAllNotes();

        List<Note> taskList = mNoteDao.getNotes();

        assertThat(taskList.size(), is(0));
    }

    @Test
    public void insertNote_getNoteCount()
    {
        mNoteDao.insertNote(NOTE);

        int N = mNoteDao.getNoteCount();

        assertThat(N, is(1));
    }

    @Test
    public void insertNote_UpdateNote()
    {
        mNoteDao.insertNote(NOTE);

        NOTE.setDescription("DESC");

        mNoteDao.updateNote(NOTE);

        Note note = mNoteDao.getNoteFromId(NOTE.getId());

        checkNote(note, NOTE.getId(), NOTE.getTitle(), NOTE.getDescription(), NOTE.getPosition());
    }

    @Test
    public void deleteNote()
    {
        mNoteDao.insertNote(NOTE);

        mNoteDao.deleteNote(NOTE);

        List<Note> taskList = mNoteDao.getNotes();

        assertThat(taskList.size(), is(0));

    }

    void checkNote(Note note,String id, String title, String description, int position)
    {
        assertThat(note, notNullValue());
        assertThat(note.getId(), is(id));
        assertThat(note.getTitle(), is(title));
        assertThat(note.getDescription(), is(description));
        assertThat(note.getPosition(), is(position));
    }

}
