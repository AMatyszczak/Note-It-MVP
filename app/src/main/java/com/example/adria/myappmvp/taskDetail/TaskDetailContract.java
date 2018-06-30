package com.example.adria.myappmvp.taskDetail;

import android.content.Intent;

import com.example.adria.myappmvp.data.Task;

/**
 * Created by adria on 10.05.2018.
 */

public interface TaskDetailContract
{
    interface View
    {
        void closeTaskDetail();
        void setPresenter(TaskDetailContract.Presenter presenter);
        void updateTask();

    }

    interface Presenter
    {
        Task getTaskFromIntent(Intent intent);
        void updateTask(String title, String description, int position);
        void updateTaskOnBackPressed();
        void notifyDataChanged();
        boolean isChanged();

    }

}
