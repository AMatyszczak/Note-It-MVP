package com.example.adria.myappmvp.noteAdd;

import android.util.Log;

import com.example.adria.myappmvp.TaskList.TaskItem;
import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.local.NoteRepository;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
        ArrayList<Task> taskList = mFragment.getTaskList();
        String noteId = mNoteRepository.insertNote(note);
        taskList = setTaskNoteId(taskList,noteId);
        mNoteRepository.insertTasks(taskList);

        mFragment.showNotes();

    }

    private ArrayList<Task> setTaskNoteId(ArrayList<Task> taskList, String noteId)
    {
        for (Task task : taskList) {
            task.setNoteId(noteId);

        }
        return taskList;
    }
}
