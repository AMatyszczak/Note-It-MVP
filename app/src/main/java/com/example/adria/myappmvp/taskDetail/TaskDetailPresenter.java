package com.example.adria.myappmvp.taskDetail;

import android.content.Intent;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.local.TaskRepository;


/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter
{
    private String mTaskID;

    private TaskDetailContract.View mFragment;
    private TaskRepository mTaskRepository;
    private boolean isChanged;


    TaskDetailPresenter(String taskId, TaskDetailContract.View fragment, TaskRepository taskRepository)
    {
        mTaskID = taskId;
        mFragment = fragment;
        mTaskRepository = taskRepository;
        isChanged = false;

        mFragment.setPresenter(this);
    }



    public Task getTaskFromIntent(Intent intent)
    {
        return mTaskRepository.getTaskFromId(mTaskID);
    }

    @Override
    public void updateTask(String title, String description, int position) {
        Task task = new Task(mTaskID, title, description, position);

        mTaskRepository.updateTask(task);
        mFragment.closeTaskDetail();
    }

    @Override
    public void updateTaskOnBackPressed()
    {
        mFragment.updateTask();
    }
    @Override
    public void notifyDataChanged() { isChanged = true; }

    @Override
    public boolean isChanged() { return isChanged; }
}
