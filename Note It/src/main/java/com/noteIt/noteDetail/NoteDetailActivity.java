package com.noteIt.noteDetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.noteIt.R;
import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.util.ActivityUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NoteDetailActivity extends DaggerAppCompatActivity {

    public final static String GET_NOTE_DETAIL = "GET_NOTE_DETAIL";
    @Inject
    public NoteDetailContract.Presenter mPresenter;

    @Inject
    public NoteDetailFragment mFragment;

    @Inject
    public String mNoteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notedetail_act);
        Toolbar toolbar = findViewById(R.id.notedetail_act_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NoteDetailFragment noteDetailFragment = (NoteDetailFragment) getSupportFragmentManager().findFragmentById(R.id.NoteFragment);
        if (noteDetailFragment == null) {
            noteDetailFragment = mFragment;
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), noteDetailFragment, R.id.contentFrame);
        }

    }

    @Override
    public void onBackPressed() {
        if (mPresenter.isChanged()) {
            mPresenter.updateNoteOnBackPressed();
        }
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }

}
