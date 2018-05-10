package com.example.adria.myappmvp.Task;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adria.myappmvp.AddTask.AddTaskActivity;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.TaskDetail.TaskDetailActivity;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment implements TaskContract.View {

    private final int ADD_TASK = 1;

    private GridView mTaskGridView;
    private LinearLayout mTaskLayout;

    private TextView mNoTaskTextView;
    private LinearLayout mNoTaskLayout;

    private TaskAdapter mTaskAdapter;
    private TaskContract.Presenter mPresenter;


    public TaskFragment()
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }
    @Override
    public void setPresenter(TaskContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskAdapter = new TaskAdapter(getContext(),new ArrayList<Task>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.task_frag, container, false);
        mTaskLayout = (LinearLayout) root.findViewById(R.id.TaskLayout);
        mTaskGridView = (GridView)root.findViewById(R.id.TaskListView);
        mTaskGridView.setAdapter(mTaskAdapter);
        mTaskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Snackbar.make(getView(),task.getTitle(),Snackbar.LENGTH_SHORT).show();
                getTaskDetail(i);

            }
        }) ;


        Button butt = (Button) root.findViewById(R.id.OhButton);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTaskList();
            }
        });


        mNoTaskTextView = (TextView) root.findViewById(R.id.NoTaskTextView);
        mNoTaskLayout = (LinearLayout) root.findViewById(R.id.NoTaskLayout);
        showNoTaskMenu(false);


        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskStart();
            }
        });
    }

    @Override
    public void updateTaskList(List<Task> taskList)
    {
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }

    @Override
    public void clearTaskList()
    {
        mPresenter.clearTasks();
        mTaskAdapter.replaceTaskList(new ArrayList<Task>(0));
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void addTaskStart()
    {
        Intent intent = new Intent(getActivity(),AddTaskActivity.class);
        startActivityForResult(intent,ADD_TASK);
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());

    }

    @Override
    public void addTask(Task task)
    {
        mTaskAdapter.addTask(task);
    }

    @Override
    public void showNoTaskMenu(boolean show)
    {
        if(show)
        {
            mTaskGridView.setVisibility(View.GONE);
            mTaskLayout.setVisibility(View.GONE);

            mNoTaskTextView.setVisibility(View.VISIBLE);
            mNoTaskLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mTaskGridView.setVisibility(View.VISIBLE);
            mTaskLayout.setVisibility(View.VISIBLE);

            mNoTaskTextView.setVisibility(View.GONE);
            mNoTaskLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void getTaskDetail(int taskFromList)
    {
        Task task = mTaskAdapter.getItem(taskFromList);
        Log.e("TAG", "Task Id:" + task.getId() );
        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra("TASKID",task.getId());

        startActivity(intent);
    }

}
