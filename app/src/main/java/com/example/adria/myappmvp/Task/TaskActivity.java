package com.example.adria.myappmvp.Task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.TaskRepository;

import javax.inject.Inject;

public class TaskActivity extends AppCompatActivity {

    @Inject public TaskPresenter mPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TaskFragment taskFragment = (TaskFragment)getSupportFragmentManager().findFragmentById(R.id.Taskfragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        mPresenter = new TaskPresenter(taskFragment, taskRepository );


    }
}
