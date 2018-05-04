package com.example.adria.myappmvp.AddTask;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adria.myappmvp.R;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {

    private TextView mTitle;
    private TextView mDescription;
    private AddTaskContract.Presenter mPresenter;


    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.addtask_frag, container, false);

        mTitle = (TextView)root.findViewById(R.id.title);
        mDescription = (TextView)root.findViewById(R.id.descrption);

        return root;
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
