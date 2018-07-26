package com.noteIt.noteDetail;


import android.content.Intent;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.notes.NoteFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.inject.Inject;


/**
 * Created by adria on 10.05.2018.
 */
public final class NoteDetailPresenter implements NoteDetailContract.Presenter {

    @Nullable
    public String mNoteID;

    private NoteDetailContract.View mFragment;
    private NoteRepository mNoteRepository;
    private boolean isChanged;


    @Inject
    NoteDetailPresenter(String noteId,  NoteRepository noteRepository) {
        this.mNoteID = noteId;
        this.mNoteRepository = noteRepository;
        this.isChanged = false;

    }

    @Override
    public void setFragment(NoteDetailContract.View fragment)
    {
        mFragment = fragment;
    }

    @Override
    public void deleteFragment()
    {
        mFragment = null;
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

        Note note = mNoteRepository.getNoteFromId(mNoteID);
        note.setTitle(title);
        note.setDescription(description);
        note.setPosition(position);

        for (Task task : tasksList)
        {
            if (task.getNoteId().equals(String.valueOf(-1))) {
                task.setNoteId(mNoteID);
                mNoteRepository.insertTask(task);
            } else {
                mNoteRepository.updateTask(task);
            }
        }
        for(Task task: mFragment.getDeletedTasks())
        {
            if(!task.getNoteId().equals(String.valueOf(-1)))
            {
                mNoteRepository.deleteTask(task);
            }
        }

        mNoteRepository.updateNote(note);
        if(mFragment != null)
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
