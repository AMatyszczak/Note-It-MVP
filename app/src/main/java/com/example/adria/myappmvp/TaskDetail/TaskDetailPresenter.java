package com.example.adria.myappmvp.TaskDetail;

import android.util.Log;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter
{
    TaskDetailContract.View mFragment;
    TaskRepository mTaskRepository;

    public TaskDetailPresenter(TaskDetailContract.View fragment, TaskRepository taskRepository)
    {
        mFragment = fragment;
        mTaskRepository = taskRepository;
        Log.e(TAG, "KONSTRUKTOR PRESNETERA" );
        mFragment.setPresenter(this);
    }

    public Task getTaskFromID(String id)
    {
        Task task = mTaskRepository.getTaskFromId(id);

//        Task task = new Task("HOLA","DESC HOLA");
//        task.setTitle("HOLA");
//        task.setDescription("DESC HOLA");
        return task;
    }


}
