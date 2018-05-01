package com.example.adria.myappmvp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by adria on 01.05.2018.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract TaskDao taskDao();
}
