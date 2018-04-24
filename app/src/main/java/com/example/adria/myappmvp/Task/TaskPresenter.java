package com.example.adria.myappmvp.Task;

/**
 * Created by adria on 24.04.2018.
 */

public class TaskPresenter implements TaskContract.Presenter
{

    TaskContract.View mFragment;

    TaskPresenter(TaskContract.View fragment)
    {
        mFragment = fragment;
    }

    @Override
    public void onItemClicked() {
        mFragment.AddTask();
    }
}
