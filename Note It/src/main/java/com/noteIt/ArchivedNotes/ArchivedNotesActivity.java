package com.noteIt.ArchivedNotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.noteIt.R;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.notes.NoteActivity;
import com.noteIt.util.ActivityUtils;

public class ArchivedNotesActivity extends AppCompatActivity implements ArchivedNotesContract
{

    private ArchivedNotesContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archived_act);
        Toolbar toolbar = findViewById(R.id.archived_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar!= null)
            {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
            }
        }
        mDrawerLayout = findViewById(R.id.archived_drawerLayout);
        NavigationView navigationView = findViewById(R.id.archived_navigationView);
        setNavigationItemSelection(navigationView);

        ArchivedNotesFragment archivedNotesFragment = (ArchivedNotesFragment)getSupportFragmentManager().findFragmentById(R.id.archived_note_fragment);
        if(archivedNotesFragment == null)
        {
            archivedNotesFragment = new ArchivedNotesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),archivedNotesFragment,R.id.contentFrame);
        }
        mPresenter = new ArchivedNotesPresenter(archivedNotesFragment, NoteRepository.getINSTANCE(this));


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

    private void setNavigationItemSelection(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_archived:
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.menu_notes:
                                Intent intent = new Intent(ArchivedNotesActivity.this, NoteActivity.class);
                                startActivity(intent);
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
