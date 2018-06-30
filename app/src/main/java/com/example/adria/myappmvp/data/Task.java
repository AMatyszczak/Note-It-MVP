package com.example.adria.myappmvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.adria.myappmvp.task.TaskFragment;

import java.util.UUID;

/**
 * Created by adria on 23.04.2018.
 */
@Entity(tableName = "task")
public final class Task
{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @Nullable
    @ColumnInfo(name = "position")
    private int mPosition;


    public Task(@Nullable String title, @Nullable String description, @Nullable int position)
    {
        this(UUID.randomUUID().toString(), title, description, position);
    }

    public Task(@NonNull String Id, @Nullable String title,@Nullable String description,@Nullable int position)
    {
        mId = Id;
        mTitle = title;
        mDescription = description;

        mPosition = position;
    }


    @NonNull
    public String getId() { return mId; }

    public void setId(String Id) {
        mId = Id;
    }

    @Nullable
    public String getTitle() { return mTitle; }

    public void setTitle(String title) { mTitle = title; }

    @Nullable
    public String getDescription() { return mDescription; }

    public void setDescription(String description) { mTitle = description;}

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }


}
