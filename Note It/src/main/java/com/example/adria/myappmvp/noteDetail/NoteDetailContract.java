package com.example.adria.myappmvp.noteDetail;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;

/**
 * Created by adria on 10.05.2018.
 */

public interface NoteDetailContract {
    interface View {
        void closeNoteDetail();

        void setPresenter(NoteDetailContract.Presenter presenter);

        void updateNote();

    }

    interface Presenter {
        Note getNote();

        ArrayList<Task> getNoteTasks();

        void updateNote(String title, String description, int position, ArrayList<Task> tasksList);

        void updateNoteOnBackPressed();

        void notifyDataChanged();

        boolean isChanged();

    }

}
