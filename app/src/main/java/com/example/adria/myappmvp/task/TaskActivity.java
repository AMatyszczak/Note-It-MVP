package com.example.adria.myappmvp.task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.TaskRepository;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    public TaskPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_act);
        Toolbar toolbar = findViewById(R.id.task_act_toolbar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
        }
        else
        {
            Log.e(TAG, "onCreate(): toolbar is null" );
        }

        TaskFragment taskFragment = (TaskFragment)getSupportFragmentManager().findFragmentById(R.id.Taskfragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        mPresenter = new TaskPresenter(taskFragment, taskRepository );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
