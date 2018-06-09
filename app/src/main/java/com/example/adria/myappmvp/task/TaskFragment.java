package com.example.adria.myappmvp.task;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adria.myappmvp.gridViewCustom.CompositeListener;
import com.example.adria.myappmvp.gridViewCustom.GridViewCustom;
import com.example.adria.myappmvp.taskAdd.TaskAddActivity;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.taskDetail.TaskDetailActivity;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment implements TaskContract.View {

    private final static int ADD_TASK = 1;
    private final static String GET_TASK_DETAIL = "GETTASKDETAIL";

    private ActionMode mActionMode;
    private GridView mFlaggedGridView;

    private GridViewCustom mTaskGridView;
    private LinearLayout mTaskLayout;

    private TextView mNoTaskTextView;
    private LinearLayout mNoTaskLayout;

    private TaskAdapter mTaskAdapter;
    private TaskContract.Presenter mPresenter;

    private FloatingActionButton mFabTaskDelete;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.task_frag, container, false);
        mTaskLayout = root.findViewById(R.id.TaskLayout);
        mTaskGridView = root.findViewById(R.id.TaskGridView);
        mTaskGridView.setAdapter(mTaskAdapter);

        mFabTaskDelete = root.findViewById(R.id.fab_deleteTask);
        mFabTaskDelete.setVisibility(View.GONE);
        mFabTaskDelete.setOnDragListener(new MyDragDeleteListener());

        mFlaggedGridView = root.findViewById(R.id.FlaggedTaskGridView);


        mTaskGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mTaskGridView.setMultiChoiceModeListener(new MyMultiChoiceListener());

        mTaskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            if(mActionMode != null)
                return;
            getTaskDetail(i);

        }
    }) ;

//        mTaskGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
//        {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
//
//                if(mActionMode != null)
//                    return true;
//
//                mActionMode = getActivity().startActionMode(new ActionBarCallback());
//                view.setSelected(true);
//
//                return true;
//            }
//        });

        mNoTaskTextView = root.findViewById(R.id.NoTaskTextView);
        mNoTaskLayout = root.findViewById(R.id.NoTaskLayout);
        showNoTaskMenu(false);

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
            mFlaggedGridView.setVisibility(View.VISIBLE);
        else
            mFlaggedGridView.setVisibility(View.INVISIBLE);
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

    void startDragAndDrop(int itemIndex)
    {
        mFabTaskDelete.setVisibility(View.VISIBLE);
        String id = mTaskAdapter.getItem(itemIndex).getId();
        if(getView() != null)
        {
            CardView cardView = getView().findViewById(R.id.taskCardView);
            ClipData.Item item = new ClipData.Item(id);
            ClipData dragData = new ClipData(id,new String[]{ ClipDescription.MIMETYPE_TEXT_PLAIN},item );
            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(cardView);
            getView().startDrag(dragData, myShadow,null,0);
        }


    }


    private class MyDragDeleteListener implements View.OnDragListener
    {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch(dragEvent.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    mFabTaskDelete.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item= dragEvent.getClipData().getItemAt(0);
                    mPresenter.deleteTask(item.getText().toString());
                    mTaskAdapter.replaceTaskList(mPresenter.getAllTasks());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    mFabTaskDelete.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }

    private class MyMultiChoiceListener implements AbsListView.MultiChoiceModeListener
    {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            View view = mTaskGridView.getAdapter().getView(i,null,mTaskGridView);
            view.setBackgroundColor(getResources().getColor(R.color.pressedColor));

            final int checkedItemCount = mTaskGridView.getCheckedItemCount();
            actionMode.setTitle(checkedItemCount + " Selected");

        }
    }

    private class ActionBarCallback implements ActionMode.Callback
    {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            actionMode.setTitle("Wo Ho");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case 1:
                    break;

                case 2 :
                    break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    }


}


