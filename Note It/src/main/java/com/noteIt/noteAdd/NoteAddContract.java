package com.noteIt.noteAdd;

import com.noteIt.data.Task;

import java.util.ArrayList;

/**
 * Created by adria on 04.05.2018.
 */

public interface NoteAddContract {
    interface View {
        void setPresenter(NoteAddContract.Presenter presenter);

        String getTitle();

        String getDescription();

        void showNotes();

        ArrayList<Task> getTaskList();

    }

    interface Presenter {
        void addNote();
    }

}
