package com.example.adria.myappmvp.task;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TaskFlaggedAdapter extends BaseAdapter
{
    private List<Task> mFlaggedTaskList;

    TaskFlaggedAdapter(ArrayList<Task> arrayList)
    {
        mFlaggedTaskList = arrayList;
    }

    @Override
    public int getCount() {
        return mFlaggedTaskList.size();
    }

    @Override
    public Task getItem(int i) {
        return mFlaggedTaskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View root = view;
        if(root == null)
        {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.task, viewGroup, false);
        }
        Task task = getItem(i);

        TextView title = root.findViewById(R.id.title);
        title.setText(task.getTitle());

        TextView description = root.findViewById(R.id.description);
        description.setText(task.getDescription());


        return root;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
