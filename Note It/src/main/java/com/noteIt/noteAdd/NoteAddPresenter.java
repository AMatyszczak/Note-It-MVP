package com.noteIt.noteAdd;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by adria on 05.05.2018.
 */
@ActivityScoped
public final class NoteAddPresenter implements NoteAddContract.Presenter {

    private NoteAddContract.View mFragment;

    public NoteRepository mNoteRepository;

    @Inject
    NoteAddPresenter( NoteRepository noteRepository) {

        mNoteRepository = noteRepository;


    }

    @Override
    public void addNote() {
        Note note = new Note(mFragment.getTitle(), mFragment.getDescription(), mNoteRepository.getNoteCount()+1);
        ArrayList<Task> taskList = mFragment.getTaskList();
        String noteId = mNoteRepository.insertNote(note);
        taskList = setTaskNoteId(taskList, noteId);
        mNoteRepository.insertTasks(taskList);

        mFragment.showNotes();

    }

    @Override
    public void bindFragment(NoteAddContract.View fragment) {
        this.mFragment = fragment;
    }

    private ArrayList<Task> setTaskNoteId(ArrayList<Task> taskList, String noteId) {
        for (Task task : taskList) {
            task.setNoteId(noteId);

        }
        return taskList;
    }
}
