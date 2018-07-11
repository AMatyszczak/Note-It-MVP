package com.example.adria.myappmvp.note;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.local.NoteRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 24.04.2018.
 */

public class NotePresenter implements NoteContract.Presenter
{
    private final int ADD_NOTE = 1;

    private NoteRepository mNoteRepository;

    private NoteContract.View mFragment;

    NotePresenter(NoteContract.View fragment, NoteRepository noteRepository)
    {
        mFragment = fragment;
        mNoteRepository = noteRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void onItemClicked() {

    }

    @Override
    public void refreshNoteList()
    {
        List<Note> list = mNoteRepository.getNotesList();
        if(list.size() == 0)
            mFragment.showNoNoteMenu(true);
        else {
            mFragment.showNoNoteMenu(false);
            mFragment.updateNoteList(list);
        }

    }

    @Override
    public void clearNotes()
    {
        mNoteRepository.deleteAllNotes();
    }

    @Override
    public List<Note> getAllNotes()
    {
        return mNoteRepository.getNotesList();
    }

    @Override
    public void deleteNotes(ArrayList<Note> notes)
    {
        for (Note note : notes) {
            mNoteRepository.deleteNote(note);
        }
        refreshNoteList();

    }

    @Override
    public void deleteNoteById(String id) {

        Note note = mNoteRepository.getNoteFromId(id);

        mNoteRepository.deleteNote(note);
        if(mNoteRepository.getNotesList().size() == 0)
            mFragment.showNoNoteMenu(true);

    }

    @Override
    public void swapNotesPositions(Note fromNote, Note toNote)
    {
        int position = fromNote.getPosition();
        fromNote.setPosition(toNote.getPosition());
        toNote.setPosition(position);

        mNoteRepository.updateNote(fromNote);
        mNoteRepository.updateNote(toNote);

    }


}
