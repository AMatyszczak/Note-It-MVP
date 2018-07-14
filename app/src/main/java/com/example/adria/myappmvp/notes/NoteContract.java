package com.example.adria.myappmvp.notes;

import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 23.04.2018.
 */

public interface NoteContract
{
    interface View
    {
        void showNoNoteMenu(boolean show);
        void addNote(Note note);
        void updateNoteList(List<Note> noteList);
        void setPresenter(NoteContract.Presenter presenter);
        void addNoteStart();
        void clearNoteList();
        void getNoteDetail(int noteFromList);

        ArrayList<Task> getNoteTasks(String noteId);


    }
    interface Presenter
    {
        void onItemClicked();
        List<Note> getAllNotes();
        void clearNotes();
        void deleteNotes(ArrayList<Note> notes);
        void deleteNoteById(String id);

        void refreshNoteList();
        ArrayList<Task> getNoteTasks(String noteId);

        public void swapNotesPositions(Note fromNote, Note toNote);
    }


}
