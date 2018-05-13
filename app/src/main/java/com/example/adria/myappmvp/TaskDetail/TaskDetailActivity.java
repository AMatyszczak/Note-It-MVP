package com.example.adria.myappmvp.TaskDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import static android.content.ContentValues.TAG;

public class TaskDetailActivity extends AppCompatActivity {

    public TaskDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.taskdetail_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.taskDetailFragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        String id = getIntent().getStringExtra("TASKID");
        mPresenter = new TaskDetailPresenter(id, taskDetailFragment,taskRepository);

    }

}
