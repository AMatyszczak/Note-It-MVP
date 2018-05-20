package com.example.adria.myappmvp.taskAdd;

/**
 * Created by adria on 04.05.2018.
 */

public interface TaskAddContract
{
    interface View
    {
        void setPresenter(TaskAddContract.Presenter presenter);
        String getTitle();
        String getDescription();
        void showTasks();

    }

    interface Presenter
    {
        void addTask();
    }

}
