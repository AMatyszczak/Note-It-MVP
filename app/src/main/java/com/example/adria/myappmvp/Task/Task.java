package com.example.adria.myappmvp.Task;

/**
 * Created by adria on 23.04.2018.
 */

public class Task
{
    public int ID;

    public String mTitle;

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

    public String getmDescription() { return mDescription; }



}
