package com.example.adria.myappmvp.Task;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.TaskRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by adria on 24.04.2018.
 */

public class TaskPresenter implements TaskContract.Presenter
{

    private TaskRepository mTaskRepository;

    private TaskContract.View mFragment;

    TaskPresenter(TaskContract.View fragment, TaskRepository taskRepository)
    {
        mFragment = fragment;
        mTaskRepository = taskRepository;
    }

    @Override
    public void onItemClicked() {
        Task task = new Task("Test", "test2");
        mTaskRepository.insertTask(task);
        mFragment.addTask(task);
        List<Task> taskList = mTaskRepository.getAllTasks();
        //mFragment.updateTaskList(taskList);
    }
}
