package com.example.adria.myappmvp.noteDetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.local.NoteRepository;


public class NoteDetailActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String GET_NOTE_DETAIL = "GETNOTEDETAIL";
    public NoteDetailPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notedetail_act);
        Toolbar toolbar = findViewById(R.id.notedetail_act_toolbar);

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


        NoteDetailFragment noteDetailFragment = (NoteDetailFragment) getSupportFragmentManager().findFragmentById(R.id.noteDetailFragment);
        NoteRepository noteRepository = NoteRepository.getINSTANCE(getApplication());
        String id = getIntent().getStringExtra(GET_NOTE_DETAIL);

        mPresenter = new NoteDetailPresenter(id, noteDetailFragment,noteRepository);

    }

    @Override
    public void onBackPressed()
    {
        if(mPresenter.isChanged())
        {
            mPresenter.updateNoteOnBackPressed();
        }
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail,menu);
        return true;
    }

}
