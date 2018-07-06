package com.example.adria.myappmvp.task;

import android.util.Log;

import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.local.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

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
    public void refreshTaskList()
    {
        List<Task> list = mTaskRepository.getTasksList();
        if(list.size() == 0)
            mFragment.showNoTaskMenu(true);
        else {
            mFragment.showNoTaskMenu(false);
            mFragment.updateTaskList(list);
        }

    }

    @Override
    public void clearTasks()
    {
        mTaskRepository.deleteAllTasks();
    }

    @Override
    public List<Task> getAllTasks()
    {
        return mTaskRepository.getTasksList();
    }

    @Override
    public void deleteTasks(ArrayList<Task> tasks)
    {
        for (Task task: tasks) {
            mTaskRepository.deleteTask(task);
        }
        refreshTaskList();

    }

    @Override
    public void deleteTaskById(String id) {

        Task task = mTaskRepository.getTaskFromId(id);

        mTaskRepository.deleteTask(task);
        if(mTaskRepository.getTasksList().size() == 0)
            mFragment.showNoTaskMenu(true);

    }

    @Override
    public void swapTasksPositions(Task fromTask, Task toTask)
    {
        int position = fromTask.getPosition();
        fromTask.setPosition(toTask.getPosition());
        toTask.setPosition(position);

        mTaskRepository.updateTask(fromTask);
        mTaskRepository.updateTask(toTask);

    }


}
