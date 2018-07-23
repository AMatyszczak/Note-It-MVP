package com.noteIt.ArchivedNotes;

import com.noteIt.data.Note;

import java.util.List;

public interface ArchivedNotesContract
{

    interface View
    {
        void setPresenter(ArchivedNotesContract.Presenter presenter);
        void updateNoteList(List<Note> noteList);
    }

    interface Presenter
    {
        void refreshNoteList();
    }

}
