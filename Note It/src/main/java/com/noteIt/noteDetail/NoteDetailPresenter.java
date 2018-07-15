package com.noteIt.noteDetail;


import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;


/**
 * Created by adria on 10.05.2018.
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter {
    private String mNoteID;

    private NoteDetailContract.View mFragment;
    private NoteRepository mNoteRepository;
    private boolean isChanged;


    NoteDetailPresenter(String noteId, NoteDetailContract.View fragment, NoteRepository noteRepository) {
        mNoteID = noteId;
        mFragment = fragment;
        mNoteRepository = noteRepository;
        isChanged = false;

        mFragment.setPresenter(this);
    }


    @Override
    public Note getNote() {
        return mNoteRepository.getNoteFromId(mNoteID);
    }

    @Override
    public ArrayList<Task> getNoteTasks() {
        return mNoteRepository.getNoteTasks(mNoteID);
    }

    @Override
    public void updateNote(String title, String description, int position, ArrayList<Task> tasksList) {
        Note note = new Note(mNoteID, title, description, position);
        for (Task task : tasksList) {
            if (task.getNoteId().equals(String.valueOf(-1))) {
                task.setNoteId(mNoteID);
                mNoteRepository.insertTask(task);
            } else {
                mNoteRepository.updateTask(task);
            }
        }

        mNoteRepository.updateNote(note);
        mFragment.closeNoteDetail();
    }

    @Override
    public void updateNoteOnBackPressed() {
        mFragment.updateNote();
    }

    @Override
    public void notifyDataChanged() {
        isChanged = true;
    }

    @Override
    public boolean isChanged() {
        return isChanged;
    }
}
