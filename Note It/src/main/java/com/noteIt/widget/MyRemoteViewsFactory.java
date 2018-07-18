package com.noteIt.widget;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.noteIt.R;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;


public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private static final String NOTE_ID = "NOTE_ID";

    private Context mContext;
    private NoteRepository mNoteRepository;
    private ArrayList<Task> mTaskArrayList;
    private String mNoteId;

    public MyRemoteViewsFactory(Context context, Intent intent)
    {
        mContext = context;
        mNoteRepository = NoteRepository.getINSTANCE(context);
        mNoteId = intent.getStringExtra(NOTE_ID);
        mTaskArrayList = mNoteRepository.getNoteTasks(mNoteId);
    }

    @Override
    public void onCreate() {
        mTaskArrayList = mNoteRepository.getNoteTasks(mNoteId);
    }

    @Override
    public void onDataSetChanged() {
        mTaskArrayList = mNoteRepository.getNoteTasks(mNoteId);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mTaskArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_task_layout);

        Task task = mTaskArrayList.get(i);
        remoteViews.setTextViewText(R.id.widget_task_text, task.getDescription());
        if(task.isDone())
        {
            remoteViews.setImageViewResource(R.id.widget_checkbox_imageView, R.drawable.ic_check_box_black_24dp);
        }
        else
        {
            remoteViews.setImageViewResource(R.id.widget_checkbox_imageView, R.drawable.ic_check_box_outline_blank_black_24dp);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
