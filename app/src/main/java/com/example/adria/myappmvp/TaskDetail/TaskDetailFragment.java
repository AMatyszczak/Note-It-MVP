package com.example.adria.myappmvp.TaskDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View
{
    private TextView mTitle;
    private TextView mDescription;

    private TaskDetailContract.Presenter mPresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        String Id = bundle.getString("TASKID");
        Log.e("TAG",Id);
        if(mPresenter == null)
            Log.e(TAG, "PRESENTER JEST PUSTY" );
        Task task = mPresenter.getTaskFromID(Id);
        mTitle.setText(task.getTitle());
        mDescription.setText(task.getDescription());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);

        mTitle = (TextView) root.findViewById(R.id.title);
        mDescription = (TextView) root.findViewById(R.id.description);


        return root;
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
        Log.e(TAG, "DODAJE PRESENTER !!!!!!!!!!!!!!" );
    }

    @Override
    public void closeTaskDetail()
    {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


}
