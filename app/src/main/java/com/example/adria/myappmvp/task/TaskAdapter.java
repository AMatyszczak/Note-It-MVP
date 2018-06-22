package com.example.adria.myappmvp.task;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * Created by adria on 23.04.2018.
 */

public class TaskAdapter extends BaseAdapter
{
    private List<Task> mTaskList;

    TaskAdapter(List<Task> TaskList)
    {

        this.mTaskList = TaskList;
        setList(TaskList);
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Task getItem(int i)
    {
        return mTaskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = view;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            root = inflater.inflate(R.layout.task, viewGroup, false);
        }

        Task task = getItem(i);

        TextView title = root.findViewById(R.id.title);
        title.setText(task.getTitle());

        TextView description = root.findViewById(R.id.description);
        description.setText(task.getDescription());


        return root;
    }
    public void addTask(Task task)
    {
        mTaskList.add(task);
        notifyDataSetChanged();

    }

    public void replaceTaskList(List<Task> taskList)
    {
        setList(taskList);
    }
    private void setList(List<Task> taskList)
    {
        mTaskList.clear();
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


    public void swapItems(int fromId, int toId)
    {
        Task temp = mTaskList.get(fromId);
        Task toIdTask = mTaskList.get(toId);
        mTaskList.set(fromId, toIdTask);
        mTaskList.set(toId, temp);

    }

    public ArrayList<Task> getTasksFromIds(SparseBooleanArray taskIds)
    {
        ArrayList<Task> taskList = new ArrayList<>() ;

        for (int i = 0 ; i < taskIds.size(); i++)
        {
            if(taskIds.valueAt(i))
            {
                 taskList.add(mTaskList.get(taskIds.keyAt(i)));
            }

        }
        return taskList;
    }
}
