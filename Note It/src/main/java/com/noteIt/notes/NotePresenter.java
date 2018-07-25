package com.noteIt.notes;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by adria on 24.04.2018.
 */
@ActivityScoped
public final class NotePresenter implements NoteContract.Presenter {
    private final int ADD_NOTE = 1;

    private final NoteRepository mNoteRepository;


    @Inject
    NotePresenter(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    public List<Note> getNotes() {
        return mNoteRepository.getNotesList();
    }

    @Override
    public ArrayList<Task> getNoteTasks(String noteId) {
        return mNoteRepository.getNoteTasks(noteId);
    }

    @Override
    public void deleteNotes(ArrayList<Note> notes) {
        mNoteRepository.deleteNotes(notes);
    }

    @Override
    public void updateNotes(ArrayList<Note> noteList) {
        mNoteRepository.updateNotes(noteList);
    }

    @Override
    public void swapNotesPositions(Note fromNote, Note toNote) {
        int position = fromNote.getPosition();
        fromNote.setPosition(toNote.getPosition());
        toNote.setPosition(position);

        mNoteRepository.updateNote(fromNote);
        mNoteRepository.updateNote(toNote);

    }
}
