package com.noteIt.archivedNotes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public final class ArchivedNotePresenter implements ArchivedNoteContract.Presenter
{

    private ArchivedNoteContract.View mFragment;
    private NoteRepository mNoteRepository;

    @Inject
    ArchivedNotePresenter(NoteRepository noteRepository)
    {
        this.mNoteRepository = noteRepository;
    }

    @Override
    public void setFragment(ArchivedNoteContract.View fragment)
    {
        mFragment = fragment;
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
