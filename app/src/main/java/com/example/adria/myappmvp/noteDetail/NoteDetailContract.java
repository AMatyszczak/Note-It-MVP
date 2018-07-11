package com.example.adria.myappmvp.noteDetail;

import android.content.Intent;

import com.example.adria.myappmvp.data.Note;

/**
 * Created by adria on 10.05.2018.
 */

public interface NoteDetailContract
{
    interface View
    {
        void closeNoteDetail();
        void setPresenter(NoteDetailContract.Presenter presenter);
        void updateNote();

    }

    interface Presenter
    {
        Note getNoteFromIntent(Intent intent);
        void updateNote(String title, String description, int position);
        void updateNoteOnBackPressed();
        void notifyDataChanged();
        boolean isChanged();

    }

}
