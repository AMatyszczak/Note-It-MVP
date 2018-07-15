package com.example.adria.myappmvp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.example.adria.myappmvp.data.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Insert
    void insertTasks(ArrayList<Task> task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);


}
