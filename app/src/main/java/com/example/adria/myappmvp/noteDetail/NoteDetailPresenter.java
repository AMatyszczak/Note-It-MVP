package com.example.adria.myappmvp.noteDetail;

import android.content.Intent;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.local.NoteRepository;


/**
 * Created by adria on 10.05.2018.
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter
{
    private String mNoteID;

    private NoteDetailContract.View mFragment;
    private NoteRepository mNoteRepository;
    private boolean isChanged;


    NoteDetailPresenter(String noteId, NoteDetailContract.View fragment, NoteRepository noteRepository)
    {
        mNoteID = noteId;
        mFragment = fragment;
        mNoteRepository = noteRepository;
        isChanged = false;

        mFragment.setPresenter(this);
    }



    public Note getNoteFromIntent(Intent intent)
    {
        return mNoteRepository.getNoteFromId(mNoteID);
    }

    @Override
    public void updateNote(String title, String description, int position) {
        Note note = new Note(mNoteID, title, description, position);

        mNoteRepository.updateNote(note);
        mFragment.closeNoteDetail();
    }

    @Override
    public void updateNoteOnBackPressed()
    {
        mFragment.updateNote();
    }
    @Override
    public void notifyDataChanged() { isChanged = true; }

    @Override
    public boolean isChanged() { return isChanged; }
}
