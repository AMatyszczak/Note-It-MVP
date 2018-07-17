package com.noteIt.TaskList;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.noteIt.R;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.MyViewHolder> {
    private ArrayList<Task> mTaskList;
    private ArrayList<Task> mDeletedTaskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        EditText editText;
        public ImageButton button;
        TextWatcher textWatcher;

        MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.task_checkbox);
            editText = view.findViewById(R.id.task_text);
            button = view.findViewById(R.id.task_clear_button);
        }
    }

    public TaskRecyclerAdapter(ArrayList<Task> arrayList) {
        this.mDeletedTaskList = new ArrayList<>(0);
        this.mTaskList = arrayList;
        setList(arrayList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View root = layoutInflater.inflate(R.layout.task, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(root);
        root.setTag(myViewHolder);

        return (MyViewHolder) root.getTag();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Task task = mTaskList.get(position);

        if (holder.textWatcher != null)
            holder.editText.removeTextChangedListener(holder.textWatcher);


        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        holder.editText.addTextChangedListener(holder.textWatcher);
        holder.editText.setText(task.getDescription());
        holder.checkBox.setChecked(task.isDone());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                task.setDone(b);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeletedTaskList.add(task);
                deleteTask(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private void deleteTask(Task task) {
        mTaskList.remove(task);
        notifyDataSetChanged();
    }

    public void addTasks(ArrayList<Task> taskList) {
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }

    public void addTask(Task task) {
        mTaskList.add(task);
        notifyDataSetChanged();
    }


    public ArrayList<Task> getTaskList() {
        return mTaskList;
    }

    public ArrayList<Task> getDeletedTaskList() { return mDeletedTaskList; };

    private void setList(List<Task> taskList) {
        mTaskList.clear();
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }

}
