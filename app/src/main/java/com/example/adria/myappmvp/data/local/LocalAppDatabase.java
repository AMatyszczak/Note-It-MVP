package com.example.adria.myappmvp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.adria.myappmvp.data.Task;

/**
 * Created by adria on 01.05.2018.
 */
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class LocalAppDatabase extends RoomDatabase
{
    public abstract TaskDao taskDao();

    private static LocalAppDatabase INSTANCE;

    static LocalAppDatabase getDatabase(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (LocalAppDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalAppDatabase.class, "App_Database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
