package com.example.adria.myappmvp.Task;

/**
 * Created by adria on 23.04.2018.
 */

public interface TaskContract
{
    public interface View
    {
        void ShowNoTaskMenu(boolean show);
        void AddTask();
    }
    public interface Presenter
    {
        void onItemClicked();
    }


}
