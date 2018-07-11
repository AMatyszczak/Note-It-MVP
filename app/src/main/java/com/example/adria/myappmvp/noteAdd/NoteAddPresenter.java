package com.example.adria.myappmvp.noteAdd;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.local.NoteRepository;

/**
 * Created by adria on 05.05.2018.
 */

public class NoteAddPresenter implements NoteAddContract.Presenter
{

    private NoteAddContract.View mFragment;
    private NoteRepository mNoteRepository;

    NoteAddPresenter(NoteAddContract.View fragment, NoteRepository noteRepository)
    {
        mFragment = fragment;
        mNoteRepository = noteRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void addNote()
    {
        Note note = new Note(mFragment.getTitle(),mFragment.getDescription(), -1);

        mNoteRepository.insertNote(note);
        mFragment.showNotes();

    }
}
