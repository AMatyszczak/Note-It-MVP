package com.example.adria.myappmvp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */
@Dao
public interface TaskDao
{
    @Query("SELECT * FROM task")
    List<Task> allTasks();

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM task")
    void delete();
}
