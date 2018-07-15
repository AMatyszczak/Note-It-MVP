package com.noteIt.notes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 23.04.2018.
 */

public interface NoteContract {
    interface View {
        void showNoNoteMenu(boolean show);



        void updateNoteList(List<Note> noteList);

        void setPresenter(NoteContract.Presenter presenter);

        void addNoteStart();



        void getNoteDetail(int noteFromList);

        ArrayList<Task> getNoteTasks(String noteId);


    }

    interface Presenter {

        List<Note> getAllNotes();

        void deleteNotes(ArrayList<Note> notes);

        void refreshNoteList();

        ArrayList<Task> getNoteTasks(String noteId);

        void swapNotesPositions(Note fromNote, Note toNote);
    }


}
