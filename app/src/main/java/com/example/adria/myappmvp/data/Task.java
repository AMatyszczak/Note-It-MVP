package com.example.adria.myappmvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;

/**
 * Created by adria on 23.04.2018.
 */
@Entity(tableName = "task")
public class Task
{
    @PrimaryKey
    public int ID;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "description")
    public String mDescription;

    public Task(String Title, String Description)
    {
        mTitle = Title;
        mDescription = Description;
    }

    public void SetId(int id ) { ID = id; }

    public void SetTitle(String title) { mTitle = title; }

    public void SetDescription(String description) { mDescription = description; }

    public int getID() { return ID; }

    public String getTitle() { return mTitle; }

    public String getDescription() { return mDescription; }



}
