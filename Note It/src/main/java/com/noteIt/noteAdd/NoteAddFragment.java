package com.noteIt.noteAdd;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.noteIt.R;
import com.noteIt.TaskList.TaskRecyclerAdapter;
import com.noteIt.data.Task;

import java.util.ArrayList;


public class NoteAddFragment extends Fragment implements NoteAddContract.View {

    private static final String ADD_LIST = "Click here to create To-Do List";
    private static final String ADD_TASK = "+ Add New Task";

    private EditText mTitle;
    private EditText mDescription;
    private NoteAddContract.Presenter mPresenter;
    private TextView mAddTaskTextView;

    private RecyclerView mRecyclerView;
    private TaskRecyclerAdapter mAdapter;


    public NoteAddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TaskRecyclerAdapter(new ArrayList<Task>(0));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_note);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNote();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.addnote_frag, container, false);

        mTitle = root.findViewById(R.id.add_note_title);
        mDescription = root.findViewById(R.id.add_note_description);


        mRecyclerView = root.findViewById(R.id.task_recycler_view_add);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAddTaskTextView = root.findViewById(R.id.add_task_text_view);
        setTaskListAddTitle(false);
        mAddTaskTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task("", false, String.valueOf(-1));
                mAdapter.addTask(task);
                mAdapter.notifyDataSetChanged();
                setTaskListAddTitle(true);

            }
        });
        return root;
    }

    @Override
    public void setPresenter(NoteAddContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }

    @Override
    public String getDescription() {
        return mDescription.getText().toString();
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return mAdapter.getTaskList();
    }

    @Override
    public void showNotes() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    public void setTaskListAddTitle(boolean set) {
        if (set) {
            mAddTaskTextView.setText(ADD_TASK);
        } else
            mAddTaskTextView.setText(ADD_LIST);
    }


}
