package com.noteIt.noteAdd;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.noteIt.R;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.util.ActivityUtils;

public class NoteAddActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private NoteAddPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnote_act);
        Toolbar toolbar = findViewById(R.id.noteAdd_act_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NoteAddFragment NoteAddFragment = (NoteAddFragment) getSupportFragmentManager().findFragmentById(R.id.NoteAddFragment);
        if (NoteAddFragment == null) {
            NoteAddFragment = new NoteAddFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), NoteAddFragment, R.id.contentFrame);
        }
        NoteRepository noteRepository = NoteRepository.getINSTANCE(getApplication());
        mPresenter = new NoteAddPresenter(NoteAddFragment, noteRepository);
    }


    @Override
    public void onBackPressed() {

        mPresenter.addNote();
        setResult(Activity.RESULT_OK);
        finish();

    }
}
