package com.example.adria.myappmvp.TaskDetail;

import android.content.Intent;
import android.util.Log;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter
{
    private String mTaskID;

    private TaskDetailContract.View mFragment;
    private TaskRepository mTaskRepository;


    public TaskDetailPresenter(String taskId, TaskDetailContract.View fragment, TaskRepository taskRepository)
    {
        mTaskID = taskId;
        mFragment = fragment;
        mTaskRepository = taskRepository;

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
}
