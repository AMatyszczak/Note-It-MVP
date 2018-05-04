package com.example.adria.myappmvp.Task;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by adria on 23.04.2018.
 */

public class TaskAdapter extends BaseAdapter
{
    private List<Task> mTaskList;
    private Context mContext;

    TaskAdapter(Context context, List<Task> TaskList)
    {
        mContext = context;
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.task, viewGroup, false);

        Task task = getItem(i);

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

    public void setList(List<Task> taskList)
    {
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
