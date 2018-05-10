package com.example.adria.myappmvp.TaskDetail;

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

    }

    interface Presenter
    {
        Task getTaskFromID(String id);
    }

}
