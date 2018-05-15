package com.example.adria.myappmvp.AddTask;

/**
 * Created by adria on 04.05.2018.
 */

public interface AddTaskContract
{
    interface View
    {
        void setPresenter(AddTaskContract.Presenter presenter);
        String getTitle();
        String getDescription();
        void showTasks();

    }

    interface Presenter
    {
        void addTask();
    }

}
