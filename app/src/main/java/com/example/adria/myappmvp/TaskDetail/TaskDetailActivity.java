package com.example.adria.myappmvp.TaskDetail;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.TaskRepository;


public class TaskDetailActivity extends AppCompatActivity {

    public TaskDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.taskDetailFragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        String id = getIntent().getStringExtra("TASKID");
        mPresenter = new TaskDetailPresenter(id, taskDetailFragment,taskRepository);

    }

    @Override
    public void onBackPressed()
    {
        if(mPresenter.isChanged())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Save changes");
            alertDialog.setMessage("Do You want to save recent changes ? ");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mPresenter.updateTaskOnBackPressed();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });
            alertDialog.show();
        }
        else
        {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
