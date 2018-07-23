package com.noteIt.ArchivedNotes;

import android.util.Log;

import com.noteIt.data.Note;
import com.noteIt.data.local.NoteRepository;

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
        for (Note note: mNoteRepository.getArchivedNotes()) {
            Log.e(TAG, "refreshNoteList: " + note.getTitle() + " , " + note.isArchived() );
        }
        mFragment.updateNoteList(mNoteRepository.getArchivedNotes());
    }
}
