package com.example.adria.myappmvp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.example.adria.myappmvp.data.Task;

@Dao
public interface TaskDao
{
    @Insert
    void updateTask(Task task);

   @Delete
   void deleteTask(Task task);

}
