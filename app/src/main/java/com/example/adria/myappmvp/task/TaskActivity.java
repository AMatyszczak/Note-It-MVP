package com.example.adria.myappmvp.task;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.local.TaskRepository;
import com.example.adria.myappmvp.util.ActivityUtils;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    public TaskPresenter mPresenter;
    public DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_act);
        Toolbar toolbar = findViewById(R.id.task_act_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);



        TaskFragment taskFragment = (TaskFragment)getSupportFragmentManager().findFragmentById(R.id.TaskFragment);
        if(taskFragment == null)
        {
            taskFragment = new TaskFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), taskFragment, R.id.contentFrame);
        }
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        mPresenter = new TaskPresenter(taskFragment, taskRepository );
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
        getMenuInflater().inflate(R.menu.menu_task,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
