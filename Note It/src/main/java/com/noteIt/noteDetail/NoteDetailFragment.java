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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noteIt.R;
import com.noteIt.TaskList.TaskRecyclerAdapter;
import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.notes.NoteFragment;
import com.noteIt.widget.NoteWidgetProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by adria on 10.05.2018.
 */
@ActivityScoped
public class NoteDetailFragment extends DaggerFragment implements NoteDetailContract.View {

    public static final String NOTE_ID = "NOTE_ID";
    private static final String ADD_LIST = "Click here to create To-Do List";
    private static final String ADD_TASK = "+ Add New Task";
    private static final String SAVED = "Saved";

    private EditText mTitle;
    private EditText mDescription;
    private int mPosition;
    private TextView mAddTaskTextView;

    @Inject
    public NoteDetailContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private TaskRecyclerAdapter mAdapter;

    @Inject
    public NoteDetailFragment()
    {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setFragment(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.deleteFragment();
    }

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
        mTitle = root.findViewById(R.id.detail_note_title);
        mDescription = root.findViewById(R.id.detail_note_description);
        if(mPresenter!= null)
        {
            mAdapter.addTasks(mPresenter.getNoteTasks());
        }


        mRecyclerView = root.findViewById(R.id.task_recycler_view_detail);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAddTaskTextView = root.findViewById(R.id.add_task_text_view);

        if (mAdapter.getItemCount() == 0)
            setTaskListAddTitle(false);
        else
            setTaskListAddTitle(true);

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
    public void updateNote() {
        mPresenter.updateNote(mTitle.getText().toString(), mDescription.getText().toString(), mPosition, mAdapter.getTaskList());
        if (getView() != null) {

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
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

    @Override
    public ArrayList<Task> getDeletedTasks() {
        if(mAdapter!=null)
        {
            return mAdapter.getDeletedTaskList();
        }
        else
            return new ArrayList<Task>(0);

    }

    public void setTaskListAddTitle(boolean set) {
        if (set) {
            mAddTaskTextView.setText(ADD_TASK);
        } else
            mAddTaskTextView.setText(ADD_LIST);
    }

}
