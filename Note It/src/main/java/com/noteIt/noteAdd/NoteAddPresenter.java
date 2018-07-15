package com.noteIt.noteAdd;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;

/**
 * Created by adria on 05.05.2018.
 */

public class NoteAddPresenter implements NoteAddContract.Presenter {

    private NoteAddContract.View mFragment;
    private NoteRepository mNoteRepository;

    NoteAddPresenter(NoteAddContract.View fragment, NoteRepository noteRepository) {
        mFragment = fragment;
        mNoteRepository = noteRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void addNote() {
        Note note = new Note(mFragment.getTitle(), mFragment.getDescription(), -1);
        ArrayList<Task> taskList = mFragment.getTaskList();
        String noteId = mNoteRepository.insertNote(note);
        taskList = setTaskNoteId(taskList, noteId);
        mNoteRepository.insertTasks(taskList);

        mFragment.showNotes();

    }

    private ArrayList<Task> setTaskNoteId(ArrayList<Task> taskList, String noteId) {
        for (Task task : taskList) {
            task.setNoteId(noteId);

        }
        return taskList;
    }
}
