package com.example.adria.myappmvp.taskDetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.local.TaskRepository;


public class TaskDetailActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String GET_TASK_DETAIL = "GETTASKDETAIL";
    public TaskDetailPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);
        Toolbar toolbar = findViewById(R.id.taskdetail_act_toolbar);

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


        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.taskDetailFragment);
        TaskRepository taskRepository = TaskRepository.getINSTANCE(getApplication());
        String id = getIntent().getStringExtra(GET_TASK_DETAIL);
        mPresenter = new TaskDetailPresenter(id, taskDetailFragment,taskRepository);

    }

    @Override
    public void onBackPressed()
    {
        if(mPresenter.isChanged())
        {
            mPresenter.updateTaskOnBackPressed();
        }
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_detail,menu);
        return true;
    }

}
