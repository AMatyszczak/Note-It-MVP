package com.noteIt.noteDetail;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.noteIt.R;
import com.noteIt.TaskList.TaskRecyclerAdapter;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.widget.NoteWidgetProvider;

import java.util.ArrayList;

/**
 * Created by adria on 10.05.2018.
 */

public class NoteDetailFragment extends Fragment implements NoteDetailContract.View {
    private static final String ADD_LIST = "Click here to create To-Do List";
    private static final String ADD_TASK = "+ Add New Task";
    private static final String SAVED = "Saved";

    private EditText mTitle;
    private EditText mDescription;
    private int mPosition;
    private TextView mAddTaskEditText;

    private NoteDetailContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private TaskRecyclerAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TaskRecyclerAdapter(new ArrayList<Task>(0));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mPresenter != null)
        {
            Note note = mPresenter.getNote();
            mTitle.setText(note.getTitle());
            mDescription.setText(note.getDescription());
            mPosition = note.getPosition();


            mTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mPresenter.notifyDataChanged();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mPresenter.notifyDataChanged();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_note_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notedetail_frag, container, false);
        mTitle = root.findViewById(R.id.edit_note_title);
        mDescription = root.findViewById(R.id.edit_note_description);
        if(mPresenter!= null)
            mAdapter.addTasks(mPresenter.getNoteTasks());

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAddTaskEditText = root.findViewById(R.id.add_note_textView);

        if (mAdapter.getItemCount() == 0)
            setTaskListAddTitle(false);
        else
            setTaskListAddTitle(true);

        mAddTaskEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(" ", false, String.valueOf(-1));
                mAdapter.addTask(task);
                mAdapter.notifyDataSetChanged();
                setTaskListAddTitle(true);

            }
        });


        return root;
    }

    @Override
    public void setPresenter(NoteDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateNote() {
        mPresenter.updateNote(mTitle.getText().toString(), mDescription.getText().toString(), mPosition, mAdapter.getTaskList());
        if (getView() != null) {

            Snackbar.make(getView(), SAVED, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void closeNoteDetail() {
        Intent intent = new Intent(getActivity(), NoteWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        getActivity().sendBroadcast(intent);

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    public void setTaskListAddTitle(boolean set) {
        if (set) {
            mAddTaskEditText.setText(ADD_TASK);
        } else
            mAddTaskEditText.setText(ADD_LIST);
    }

}
