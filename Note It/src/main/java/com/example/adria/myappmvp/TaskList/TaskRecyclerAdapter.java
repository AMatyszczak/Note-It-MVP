package com.example.adria.myappmvp.TaskList;

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

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.MyViewHolder> {
    private ArrayList<Task> mTaskItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        EditText editText;
        public ImageButton button;
        TextWatcher textWatcher;

        MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.item_checkbox);
            editText = view.findViewById(R.id.item_text);
            button = view.findViewById(R.id.clear_button);
        }
    }

    public TaskRecyclerAdapter(ArrayList<Task> arrayList) {
        this.mTaskItems = arrayList;
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
        final Task task = mTaskItems.get(position);

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
                deleteTask(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskItems.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private void deleteTask(Task task) {
        mTaskItems.remove(task);
        notifyDataSetChanged();
    }

    public void addTasks(ArrayList<Task> taskList) {
        mTaskItems.addAll(taskList);
        notifyDataSetChanged();
    }

    public void addTask(Task task) {
        mTaskItems.add(task);
        notifyDataSetChanged();
    }


    public ArrayList<Task> getTaskList() {
        return mTaskItems;
    }

    private void setList(List<Task> taskList) {
        mTaskItems.clear();
        mTaskItems.addAll(taskList);
        notifyDataSetChanged();
    }

}
