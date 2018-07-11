package com.example.adria.myappmvp.TaskList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.adria.myappmvp.R;

import java.util.List;

public class TaskListAdapter extends BaseAdapter
{
    List<TaskItem> mTaskItems;



    @Override
    public int getCount() {
        return mTaskItems.size();
    }

    @Override
    public TaskItem getItem(int i) {
        return mTaskItems.get(i);
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
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            root = layoutInflater.inflate(R.layout.task_list_item, viewGroup, false);
        }

        TaskItem taskItem = getItem(i);

        CheckBox checkbox = root.findViewById(R.id.item_checkbox);
        checkbox.setChecked(taskItem.isDone());

        EditText editText = root.findViewById(R.id.item_text);
        editText.setText(taskItem.getDescription());

        return root;
    }
}
