package com.example.adria.myappmvp.TaskList;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Note;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TaskListAdapter extends BaseAdapter
{
    List<TaskItem> mTaskItems;

    static class ViewHolder {
        public CheckBox checkBox;
        public EditText editText;
        public TextWatcher textWatcher;
    }

    public TaskListAdapter(ArrayList<TaskItem> arrayList)
    {
        this.mTaskItems = arrayList;
        setList(arrayList);
    }


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
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.checkBox = root.findViewById(R.id.item_checkbox);
            viewHolder.editText = root.findViewById(R.id.item_text);
            root.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) root.getTag();
        if (holder.textWatcher != null)
            holder.editText.removeTextChangedListener(holder.textWatcher);


        final TaskItem taskItem = getItem(i);
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskItem.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
//        if(taskItem!= null)
//        {
//            CheckBox checkbox = root.findViewById(R.id.item_checkbox);
//            checkbox.setChecked(taskItem.isDone());
//
//            EditText editText = root.findViewById(R.id.item_text);
//            editText.setText(taskItem.getDescription());
//            editText.setTag(taskItem.getDescription());
//        }

        // Keep a reference to the TextWatcher so that we can remove it later

        holder.editText.addTextChangedListener(holder.textWatcher);
        holder.editText.setText(taskItem.getDescription());
        holder.checkBox.setChecked(taskItem.isDone());

        return root;
    }

    public void addTask(TaskItem taskItem)
    {
        mTaskItems.add(taskItem);
        notifyDataSetChanged();
    }

    private void setList(List<TaskItem> taskList)
    {
        mTaskItems.clear();
        mTaskItems.addAll(taskList);
        notifyDataSetChanged();
    }

}
