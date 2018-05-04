package com.example.adria.myappmvp.data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class TaskRepository
{
    private TaskDao mtaskDao;
    private List<Task> allTasks;
    private static TaskRepository INSTANCE = null;

    public TaskRepository(Application application)
    {
        AppDatabase database = AppDatabase.getDatabase(application);
        mtaskDao = database.taskDao();
        allTasks = mtaskDao.allTasks();
    }

    public List<Task> getAllTasks()
    {
        return allTasks;
    }

    public void insertTask(Task task)
    {
        mtaskDao.insertTask(task);
    }

    public void deleteTask()
    {
        mtaskDao.delete();
    }

    public TaskRepository getINSTANCE(Application application)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new TaskRepository(application);
        }
        return INSTANCE;
    }



}

