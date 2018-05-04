package com.example.adria.myappmvp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by adria on 01.05.2018.
 */
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract TaskDao taskDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "App_Database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
