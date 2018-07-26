package com.noteIt.noteDetail;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.notes.NoteContract;

import java.util.ArrayList;

/**
 * Created by adria on 10.05.2018.
 */

public interface NoteDetailContract {
    interface View {
        void closeNoteDetail();

        void updateNote();

        ArrayList<Task> getDeletedTasks();

    }

    interface Presenter {
        Note getNote();

        ArrayList<Task> getNoteTasks();

        void updateNote(String title, String description, int position, ArrayList<Task> tasksList);

        void updateNoteOnBackPressed();

        void notifyDataChanged();

        boolean isChanged();

        void setFragment(NoteDetailContract.View fragment);

        void deleteFragment();

    }

}
