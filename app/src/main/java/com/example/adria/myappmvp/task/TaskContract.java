package com.example.adria.myappmvp.task;

import android.util.SparseBooleanArray;

import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
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
        void getTaskDetail(int taskFromList);


    }
    interface Presenter
    {
        void onItemClicked();
        List<Task> getAllTasks();
        void clearTasks();
        void deleteTasks(ArrayList<Task> tasks);
        void deleteTaskById(String id);
        void refreshTaskList();
        public void swapTasksPositions(Task fromTask, Task toTask);
    }


}
