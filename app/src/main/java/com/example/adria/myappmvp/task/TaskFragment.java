package com.example.adria.myappmvp.task;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adria.myappmvp.gridViewCustom.GridViewCustom;
import com.example.adria.myappmvp.taskAdd.TaskAddActivity;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.taskDetail.TaskDetailActivity;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment implements TaskContract.View {


    private TaskAdapter mTaskAdapter;
    private TaskAdapter mTaskFlaggedAdapter;
    private TaskContract.Presenter mPresenter;

    private final static int ADD_TASK = 1;
    private final static String GET_TASK_DETAIL = "GETTASKDETAIL";

    private GridView mFlaggedGridView;
    private TextView mFlaggedTaskTextView;
    private TextView mTaskTextView;

    private GridViewCustom mTaskGridView;
    private LinearLayout mTaskLayout;

    private TextView mNoTaskTextView;
    private LinearLayout mNoTaskLayout;


    public TaskFragment()
    {

    }

    @Override
    public void onResume()
    {
        mPresenter.refreshTaskList();
        super.onResume();
    }
    @Override
    public void setPresenter(TaskContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskAdapter = new TaskAdapter(new ArrayList<Task>(0));
        mTaskFlaggedAdapter = new TaskAdapter(new ArrayList<Task>(0));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.task_frag, container, false);
        mTaskLayout = root.findViewById(R.id.TaskLayout);
        mNoTaskTextView = root.findViewById(R.id.NoTaskTextView);
        mNoTaskLayout = root.findViewById(R.id.NoTaskLayout);

        mTaskGridView = root.findViewById(R.id.TaskGridView);
        mTaskGridView.setAdapter(mTaskAdapter);
        mTaskGridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mTaskGridView.setMultiChoiceModeListener(new MyMultiChoiceListener(mTaskAdapter));

        showNoTaskMenu(false);

        mFlaggedGridView = root.findViewById(R.id.FlaggedTaskGridView);
        mFlaggedGridView.setAdapter(mTaskFlaggedAdapter);
        mFlaggedGridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mFlaggedGridView.setMultiChoiceModeListener(new MyMultiChoiceListener(mTaskFlaggedAdapter));

        mFlaggedTaskTextView = root.findViewById(R.id.FlaggedTaskTextView);
        mTaskTextView = root.findViewById(R.id.TaskTextView);

        showFlaggedGridView(false);



        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskStart();
            }
        });
        mPresenter.refreshTaskList();
    }

    @Override
    public void updateTaskList(List<Task> taskList)
    {
        mTaskAdapter.replaceTaskList(taskList);
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
        Intent intent = new Intent(getActivity(),TaskAddActivity.class);
        startActivityForResult(intent,ADD_TASK);
        mTaskAdapter.replaceTaskList(mPresenter.getAllTasks());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTaskAdapter.replaceTaskList(mPresenter.getAllTasks());
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

    public void showFlaggedGridView(boolean show)
    {
        if(show)
        {
            mFlaggedGridView.setVisibility(View.VISIBLE);
            mFlaggedTaskTextView.setVisibility(View.VISIBLE);
            mTaskTextView.setVisibility(View.VISIBLE);
        }

        else
        {
            //mFlaggedGridView.setVisibility(View.GONE);
            mFlaggedTaskTextView.setVisibility(View.GONE);
            mTaskTextView.setVisibility(View.GONE);
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void getTaskDetail(int taskFromList)
    {
        Task task = mTaskAdapter.getItem(taskFromList);
        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra(GET_TASK_DETAIL,task.getId());

        startActivity(intent);
    }

    private class MyMultiChoiceListener implements AbsListView.MultiChoiceModeListener
    {

        TaskAdapter skAdapter;
        MyMultiChoiceListener(TaskAdapter taskAdapter)
        {
            skAdapter = taskAdapter;
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            ArrayList<Task> arrayList = skAdapter.getTasksFromIds(mTaskGridView.getCheckedItemPositions());
            arrayList.addAll(skAdapter.getTasksFromIds(mFlaggedGridView.getCheckedItemPositions()));
            switch(menuItem.getItemId())
            {
                case R.id.item_delete:

                    mPresenter.deleteTasks(arrayList);
                    actionMode.finish();
                    break;
                case R.id.flag:

                    for (Task task :arrayList)
                    {
                        if(task.isFlagged())
                        {
                            mTaskAdapter.addTask(task);
                            mTaskFlaggedAdapter.removeTask(task);
                            task.setFlag(false);
                        }
                        else
                        {
                            mTaskFlaggedAdapter.addTask(task);
                            mTaskAdapter.removeTask(task);
                            task.setFlag(true);
                        }
                        Log.e(TAG, "onActionItemClicked: " + task.getTitle() + " , " + task.isFlagged() );
                        actionMode.finish();
                    }
                    showFlaggedGridView(true);
                    if(mFlaggedGridView.getCount() == 0)
                        showFlaggedGridView(false);

                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            int checkedItemCountTask = mTaskGridView.getCheckedItemCount();
            int checkedItemCountFlaggedTask = mFlaggedGridView.getCheckedItemCount();
            actionMode.setTitle(checkedItemCountTask + checkedItemCountFlaggedTask + " Selected");

        }
    }

}


