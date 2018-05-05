package com.example.adria.myappmvp.AddTask;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

/**
 * Created by adria on 05.05.2018.
 */

public class AddTaskPresenter implements AddTaskContract.Presenter
{

    private AddTaskContract.View mFragment;
    private TaskRepository mTaskRepository;

    AddTaskPresenter(AddTaskContract.View fragment, TaskRepository taskRepository)
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
