package com.example.adria.myappmvp.Task;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.ContentValues.TAG;
import static dagger.internal.Preconditions.checkNotNull;


/**
 * Created by adria on 23.04.2018.
 */

public class TaskAdapter extends BaseAdapter
{
    private List<Task> mTaskList;
    private Context mContext;

    TaskAdapter(Context context, List<Task> TaskList)
    {

        this.mContext = context;
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root = inflater.inflate(R.layout.task, viewGroup, false);
        }

        Task task = (Task)getItem(i);

        TextView title = (TextView)root.findViewById(R.id.title);
        title.setText(task.getTitle());

        TextView description = (TextView)root.findViewById(R.id.descrption);
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
}
