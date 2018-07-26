package com.noteIt.noteAdd;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.noteIt.R;
import com.noteIt.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NoteAddActivity extends DaggerAppCompatActivity {

    @Inject
    public NoteAddPresenter mPresenter;
    @Inject
    public NoteAddFragment mFragment;

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

        NoteAddFragment NoteAddFragment = (NoteAddFragment)getSupportFragmentManager().findFragmentById(R.id.NoteAddFragment);

        if (NoteAddFragment == null) {
            NoteAddFragment = mFragment;
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), NoteAddFragment, R.id.contentFrame);
        }
    }


    @Override
    public void onBackPressed() {

        mPresenter.addNote();
        setResult(Activity.RESULT_OK);
        finish();

    }
}
