package com.noteIt.ArchivedNotes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface ArchivedNotesContract {

    interface View {
        void setPresenter(ArchivedNotesContract.Presenter presenter);

        void updateNoteList(List<Note> noteList);

    }

    interface Presenter {
        void refreshNoteList();

        void updateNotes(ArrayList<Note> noteList);

        void deleteNotes(ArrayList<Note> noteList);

        ArrayList<Task> getNoteTasks(String noteId);

        ArrayList<Note> getNotes();

    }

}
