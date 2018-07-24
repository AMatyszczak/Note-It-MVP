package com.noteIt.ArchivedNotes;

import android.util.Log;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ArchivedNotesPresenter implements ArchivedNotesContract.Presenter
{

    private ArchivedNotesFragment mFragment;
    private NoteRepository mNoteRepository;

    public ArchivedNotesPresenter(ArchivedNotesFragment fragment, NoteRepository noteRepository)
    {
        this.mFragment = fragment;
        this.mNoteRepository = noteRepository;
        mFragment.setPresenter(this);
    }

    @Override
    public void refreshNoteList() {
        mFragment.updateNoteList(mNoteRepository.getArchivedNotes());
    }

    @Override
    public void updateNotes(ArrayList<Note> noteList) {
        mNoteRepository.updateNotes(noteList);
    }

    @Override
    public void deleteNotes(ArrayList<Note> noteList) {
        mNoteRepository.deleteNotes(noteList);
    }

    @Override
    public ArrayList<Note> getNotes() {
        return mNoteRepository.getArchivedNotes();
    }

    @Override
    public ArrayList<Task> getNoteTasks(String noteId) {
        return mNoteRepository.getNoteTasks(noteId);
    }
}
