package com.example.adria.myappmvp.noteDetail;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.TaskList.TaskItem;
import com.example.adria.myappmvp.TaskList.TaskListAdapter;
import com.example.adria.myappmvp.data.Note;
import com.example.adria.myappmvp.data.local.TaskDao_Impl;
import com.example.adria.myappmvp.widget.NoteWidgetProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class NoteDetailFragment extends Fragment implements NoteDetailContract.View
{
    private static final String SAVED = "Saved";
    private EditText mTitle;
    private EditText mDescription;
    private int mPosition;
    private TextView mAddTaskEditText;

    private NoteDetailContract.Presenter mPresenter;

    private ListView mListView;
    private TaskListAdapter mAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TaskListAdapter(new ArrayList<TaskItem>(0));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Note note = mPresenter.getNoteFromIntent(getActivity().getIntent());
        mTitle.setText(note.getTitle());
        mDescription.setText(note.getDescription());
        mDescription.setBackground(null);

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.notedetail_frag, container, false);
        mTitle = root.findViewById(R.id.title);
        mDescription = root.findViewById(R.id.description);

        mListView = root.findViewById(R.id.taskList);
        mListView.setAdapter(mAdapter);

        mAddTaskEditText = root.findViewById(R.id.add_task_textView);
        mAddTaskEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskItem taskItem = new TaskItem(" ",false);
                mAdapter.addTask(taskItem);
                mAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void setPresenter(NoteDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateNote()
    {
        mPresenter.updateNote(mTitle.getText().toString(), mDescription.getText().toString(), mPosition);
        if(getView()!=null)
        {
            Snackbar.make(getView(),SAVED,Snackbar.LENGTH_SHORT).show();
        }
        else
            Log.e(TAG, "updateNote: getView() is null");
    }

    @Override
    public void closeNoteDetail()
    {


        Intent intent = new Intent(getActivity(), NoteWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        getActivity().sendBroadcast(intent);

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


}
