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
        mTaskList = TaskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return mTaskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mTaskList.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.task, viewGroup, false);

        TextView title = (TextView)root.findViewById(R.id.title);
        TextView description = (TextView)root.findViewById(R.id.descrption);
        title.setText("TYTUL");
        description.setText("OPIS");

        return root;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
