package com.example.adria.myappmvp.noteAdd;

/**
 * Created by adria on 04.05.2018.
 */

public interface NoteAddContract
{
    interface View
    {
        void setPresenter(NoteAddContract.Presenter presenter);
        String getTitle();
        String getDescription();
        void showNotes();

    }

    interface Presenter
    {
        void addNote();
    }

}
