package com.example.adria.myappmvp.AddTask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.TaskRepository;

public class AddTaskActivity extends AppCompatActivity {

    private AddTaskPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);

        AddTaskFragment addTaskFragment = (AddTaskFragment)getSupportFragmentManager().findFragmentById(R.id.addTaskFragment);
        TaskRepository taskRepository = new TaskRepository(getApplication());
        mPresenter = new AddTaskPresenter(addTaskFragment, taskRepository);
    }

}
