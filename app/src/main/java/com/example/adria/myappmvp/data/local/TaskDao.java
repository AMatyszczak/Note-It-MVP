package com.example.adria.myappmvp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.adria.myappmvp.data.Task;

import java.util.List;

/**
 * Created by adria on 01.05.2018.
 */
@Dao
public interface TaskDao
{
    @Query("SELECT * FROM task ORDER BY position ASC")
    List<Task> getTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Query("SELECT * From task WHERE id = :id")
    Task getTaskFromId(String id);

    @Query("DELETE FROM task")
    void deleteAllTasks();

    @Query("SELECT COUNT() FROM task")
    int getTaskCount();

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);
}
