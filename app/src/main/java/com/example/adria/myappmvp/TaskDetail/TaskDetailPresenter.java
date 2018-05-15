package com.example.adria.myappmvp.TaskDetail;

import android.content.Intent;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;


/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter
{
    private String mTaskID;

    private TaskDetailContract.View mFragment;
    private TaskRepository mTaskRepository;
    private boolean isChanged;


    public TaskDetailPresenter(String taskId, TaskDetailContract.View fragment, TaskRepository taskRepository)
    {
        mTaskID = taskId;
        mFragment = fragment;
        mTaskRepository = taskRepository;
        isChanged = false;

        mFragment.setPresenter(this);
    }



    public Task getTaskFromIntent(Intent intent)
    {
        Task task = mTaskRepository.getTaskFromId(mTaskID);
        return task;
    }

    @Override
    public void updateTask(String title, String description) {
        Task task = new Task(mTaskID, title, description);
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
