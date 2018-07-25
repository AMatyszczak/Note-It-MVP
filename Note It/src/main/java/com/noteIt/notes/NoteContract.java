package com.noteIt.notes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 23.04.2018.
 */

public interface NoteContract {
    interface View {

        void addNoteStart();

        ArrayList<Task> getNoteTasks(String noteId);

        void showSnackBar(String text);


    }

    interface Presenter {

        List<Note> getNotes();

        ArrayList<Task> getNoteTasks(String noteId);

        void deleteNotes(ArrayList<Note> noteList);

        void updateNotes(ArrayList<Note> noteList);

        void swapNotesPositions(Note fromNote, Note toNote);
    }


}
