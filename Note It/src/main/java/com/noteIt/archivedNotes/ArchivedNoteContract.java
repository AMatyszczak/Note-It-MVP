package com.noteIt.archivedNotes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.List;

public interface ArchivedNoteContract {

    interface View {

        void updateNoteList(List<Note> noteList);

    }

    interface Presenter {
        void refreshNoteList();

        void updateNotes(ArrayList<Note> noteList);

        void deleteNotes(ArrayList<Note> noteList);

        ArrayList<Task> getNoteTasks(String noteId);

        ArrayList<Note> getNotes();

        void setFragment(ArchivedNoteContract.View fragment);
    }

}
