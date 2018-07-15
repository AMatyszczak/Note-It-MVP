package com.example.adria.myappmvp.TaskList;


public class TaskItem {
    private String mDescription;
    private boolean isDone;

    public TaskItem(String description, boolean done) {
        mDescription = description;
        isDone = done;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
