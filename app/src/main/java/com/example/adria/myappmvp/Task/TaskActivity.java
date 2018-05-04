package com.example.adria.myappmvp.Task;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adria.myappmvp.AddTask.AddTaskActivity;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import java.util.UUID;

import javax.inject.Inject;

public class TaskActivity extends AppCompatActivity {

    @Inject public TaskPresenter mPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TaskFragment taskFragment = (TaskFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        TaskRepository taskRepository = new TaskRepository(getApplication());
        mPresenter = new TaskPresenter(taskFragment, taskRepository );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
