package com.example.adria.myappmvp.noteAdd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.local.NoteRepository;

public class NoteAddActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private final String SAVE_ONBACKQUESTION = "Do You want to add this note";
    private final String NO = "No";
    private final String YES = "Yes";
    private NoteAddPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnote_act);
        Toolbar toolbar = findViewById(R.id.noteAdd_act_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            else
                Log.e(TAG, "onCreate(): getSupportActionBar() is null"  );
        }
        else
        {
            Log.e(TAG, "onCreate(): toolbar is null" );
        }

        NoteAddFragment NoteAddFragment = (NoteAddFragment)getSupportFragmentManager().findFragmentById(R.id.NoteAddFragment);
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
