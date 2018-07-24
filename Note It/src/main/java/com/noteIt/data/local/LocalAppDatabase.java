package com.noteIt.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

/**
 * Created by adria on 01.05.2018.
 */
@Database(entities = {Note.class, Task.class}, version = 1)
public abstract class LocalAppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    public abstract TaskDao taskDao();

    private static LocalAppDatabase INSTANCE;

    public static LocalAppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalAppDatabase.class, "App_Database").fallbackToDestructiveMigration().allowMainThreadQueries()
                            .addMigrations().build();
                }
            }
        }
        return INSTANCE;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE `note` ADD `archived`");
//        }
//    };
//
//    private static final Migration MIGRATION_2_3 = new Migration(2,3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `task` ( `id` TEXT NOT NULL, `description` TEXT , `noteId` TEXT NOT NULL," +
//                    " PRIMARY KEY(`id`))");
//        }
//    };


}
