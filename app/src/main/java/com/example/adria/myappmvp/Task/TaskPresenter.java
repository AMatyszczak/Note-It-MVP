package com.example.adria.myappmvp.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by adria on 24.04.2018.
 */

public class TaskPresenter implements TaskContract.Presenter
{
    private final int ADD_TASK = 1;

    private TaskRepository mTaskRepository;

    private TaskContract.View mFragment;

    TaskPresenter(TaskContract.View fragment, TaskRepository taskRepository)
    {
        mFragment = fragment;
        mTaskRepository = taskRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void onItemClicked() {

    }

    @Override
    public void clearTasks() {
        mTaskRepository.deleteTask();
    }

    @Override
    public List<Task> GetAllTasks()
    {
        return mTaskRepository.getAllTasks();
    }

}
