package com.example.adria.myappmvp.notes;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.local.NoteRepository;
import com.example.adria.myappmvp.util.ActivityUtils;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    public NotePresenter mPresenter;
    public DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_act);
        Toolbar toolbar = findViewById(R.id.note_act_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);



        NoteFragment noteFragment = (NoteFragment)getSupportFragmentManager().findFragmentById(R.id.NoteFragment);
        if(noteFragment == null)
        {
            noteFragment = new NoteFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), noteFragment, R.id.contentFrame);
        }
        NoteRepository noteRepository = NoteRepository.getINSTANCE(getApplication());
        mPresenter = new NotePresenter(noteFragment, noteRepository );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
