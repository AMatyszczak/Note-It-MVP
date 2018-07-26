package com.noteIt.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.noteIt.archivedNotes.ArchivedNoteActivity;
import com.noteIt.R;
import com.noteIt.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;


public class NoteActivity extends DaggerAppCompatActivity {

    @Inject
    public NotePresenter mPresenter;
    @Inject
    Lazy<NoteFragment> mNoteFragmentProvider;
    public DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_act);
        Toolbar toolbar = findViewById(R.id.note_act_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        if(navigationView!= null)
        {
            setNavigationItemSelection(navigationView);
        }

        NoteFragment noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentById(R.id.NoteFragment);
        if (noteFragment == null) {
            noteFragment = mNoteFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), noteFragment, R.id.contentFrame);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setNavigationItemSelection(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_archived:
                                Intent intent = new Intent(NoteActivity.this, ArchivedNoteActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.menu_notes:
                                mDrawerLayout.closeDrawers();
                            default:
                                break;
                        }

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
