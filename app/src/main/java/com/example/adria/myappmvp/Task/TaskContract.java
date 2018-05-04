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
    }
    interface Presenter
    {
        void onItemClicked();
    }


}
