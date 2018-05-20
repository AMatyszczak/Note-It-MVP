package com.example.adria.myappmvp.taskAdd;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.TaskRepository;

import javax.security.auth.login.LoginException;

public class TaskAddActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private final String SAVE_ONBACKQUESTION = "Do You want to add this task";
    private final String NO = "No";
    private final String YES = "Yes";
    private TaskAddPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
        Toolbar toolbar = findViewById(R.id.taskAdd_act_toolbar);
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

        TaskAddFragment TaskAddFragment = (TaskAddFragment)getSupportFragmentManager().findFragmentById(R.id.TaskAddFragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        mPresenter = new TaskAddPresenter(TaskAddFragment, taskRepository);
    }



    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(SAVE_ONBACKQUESTION);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.addTask();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }
}
