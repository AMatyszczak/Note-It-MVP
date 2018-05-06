package com.example.adria.myappmvp.data;

import android.app.Application;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class TaskRepository
{
    private TaskDao mTaskDao;
    private static TaskRepository INSTANCE = null;

    public TaskRepository(Application application)
    {
        AppDatabase database = AppDatabase.getDatabase(application);
        mTaskDao = database.taskDao();
    }

    public List<Task> getTasksList()
    {
        return mTaskDao.getTasks();
    }

    public void insertTask(Task task)
    {
        mTaskDao.insertTask(task);
    }

    public void deleteTask()
    {
        mTaskDao.delete();
    }

    public static TaskRepository getINSTANCE(Application application)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new TaskRepository(application);
        }
        return INSTANCE;
    }



}

