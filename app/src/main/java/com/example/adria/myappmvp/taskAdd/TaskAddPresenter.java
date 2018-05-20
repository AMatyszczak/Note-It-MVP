package com.example.adria.myappmvp.taskAdd;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

/**
 * Created by adria on 05.05.2018.
 */

public class TaskAddPresenter implements TaskAddContract.Presenter
{

    private TaskAddContract.View mFragment;
    private TaskRepository mTaskRepository;

    TaskAddPresenter(TaskAddContract.View fragment, TaskRepository taskRepository)
    {
        mFragment = fragment;
        mTaskRepository = taskRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void addTask()
    {
        Task task = new Task(mFragment.getTitle(),mFragment.getDescription());
        mTaskRepository.insertTask(task);
        mFragment.showTasks();

    }
}
