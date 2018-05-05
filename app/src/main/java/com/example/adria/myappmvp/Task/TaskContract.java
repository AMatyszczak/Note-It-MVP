package com.example.adria.myappmvp.Task;

import com.example.adria.myappmvp.data.Task;

import java.util.List;

/**
 * Created by adria on 23.04.2018.
 */

public interface TaskContract
{
    interface View
    {
        void showNoTaskMenu(boolean show);
        void addTask(Task task);
        void updateTaskList(List<Task> taskList);
        void setPresenter(TaskContract.Presenter presenter);
        void addTaskStart();
        void clearTaskList();

    }
    interface Presenter
    {
        void onItemClicked();
        List<Task> GetAllTasks();
        void clearTasks();
    }


}
