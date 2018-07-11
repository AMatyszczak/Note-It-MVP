package com.example.adria.myappmvp.note;

import com.example.adria.myappmvp.data.Note;

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


    }
    interface Presenter
    {
        void onItemClicked();
        List<Note> getAllNotes();
        void clearNotes();
        void deleteNotes(ArrayList<Note> notes);
        void deleteNoteById(String id);
        void refreshNoteList();
        public void swapNotesPositions(Note fromNote, Note toNote);
    }


}
