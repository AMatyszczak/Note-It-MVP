package com.noteIt.ArchivedNotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.noteIt.R;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.util.ActivityUtils;

public class ArchivedNotesActivity extends AppCompatActivity implements ArchivedNotesContract
{

    private ArchivedNotesContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archived_act);
        Toolbar toolbar = findViewById(R.id.archived_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);

        }

        ArchivedNotesFragment archivedNotesFragment = (ArchivedNotesFragment)getSupportFragmentManager().findFragmentById(R.id.archived_note_fragment);
        if(archivedNotesFragment == null)
        {
            archivedNotesFragment = new ArchivedNotesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),archivedNotesFragment,R.id.contentFrame);
        }
        mPresenter = new ArchivedNotesPresenter(archivedNotesFragment, NoteRepository.getINSTANCE(this));
    }
}
