package com.example.adria.myappmvp.data.local;

import android.app.Application;
import android.content.Context;

import com.example.adria.myappmvp.data.Task;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */

public class TaskRepository
{
    private TaskDao mTaskDao;
    private static TaskRepository INSTANCE = null;

    private TaskRepository(Context context)
    {
        LocalAppDatabase database = LocalAppDatabase.getDatabase(context);
        mTaskDao = database.taskDao();
    }

    public List<Task> getTasksList()
    {
        return mTaskDao.getTasks();
    }

    public void insertTask(Task task)
    {
        if(task.getPosition() < 0)
            task.setPosition(getTaskCount()+1);
        mTaskDao.insertTask(task);
    }

    public void deleteAllTasks()
    {
        mTaskDao.deleteAllTasks();
    }

    public Task getTaskFromId(String id) { return mTaskDao.getTaskFromId(id); }

    public void updateTask(Task task) { mTaskDao.updateTask(task); }

    public void deleteTask(Task task) { mTaskDao.deleteTask(task);}

    public int getTaskCount() { return mTaskDao.getTaskCount(); }

    public static TaskRepository getINSTANCE(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new TaskRepository(context);
        }
        return INSTANCE;
    }


}

