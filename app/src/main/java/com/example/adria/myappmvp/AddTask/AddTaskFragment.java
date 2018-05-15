package com.example.adria.myappmvp.AddTask;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.adria.myappmvp.R;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {

    private EditText mTitle;
    private EditText mDescription;
    private AddTaskContract.Presenter mPresenter;


    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addTask();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.addtask_frag, container, false);

        mTitle = (EditText)root.findViewById(R.id.title);
        mDescription = (EditText)root.findViewById(R.id.description);

        return root;
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getTitle()
    {
        return mTitle.getText().toString();
    }

    @Override
    public String getDescription()
    {
        return mDescription.getText().toString();
    }

    @Override
    public void showTasks()
    {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


}
