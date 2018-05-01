package com.example.adria.myappmvp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */
@Dao
public interface TaskDao
{
    @Query("SELECT * FROM task")
    List<Task> AllTasks();
}
