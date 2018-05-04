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
        public void updateTaskList(List<Task> taskList);
        public void setPresenter(TaskContract.Presenter presenter);
        public void addTaskStart();
    }
    interface Presenter
    {
        void onItemClicked();
        public List<Task> GetAllTasks();
    }


}
