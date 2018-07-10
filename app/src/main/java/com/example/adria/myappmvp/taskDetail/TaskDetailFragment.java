package com.example.adria.myappmvp.taskDetail;

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

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.widget.TaskWidgetProvider;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View
{
    private static final String SAVED = "Saved";
    private EditText mTitle;
    private EditText mDescription;
    private int mPosition;

    private TaskDetailContract.Presenter mPresenter;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Task task = mPresenter.getTaskFromIntent(getActivity().getIntent());
        mTitle.setText(task.getTitle());
        mDescription.setText(task.getDescription());
        mDescription.setBackground(null);
        mPosition = task.getPosition();
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

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_task_detail);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
        mTitle = root.findViewById(R.id.title);
        mDescription = root.findViewById(R.id.description);

        return root;
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateTask()
    {
        mPresenter.updateTask(mTitle.getText().toString(), mDescription.getText().toString(), mPosition);
        if(getView()!=null)
        {
            Snackbar.make(getView(),SAVED,Snackbar.LENGTH_SHORT).show();
        }
        else
            Log.e(TAG, "updateTask: getView() is null");
    }

    @Override
    public void closeTaskDetail()
    {


        Intent intent = new Intent(getActivity(), TaskWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        getActivity().sendBroadcast(intent);

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


}
