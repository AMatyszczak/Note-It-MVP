package com.example.adria.myappmvp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.local.TaskRepository;
import com.example.adria.myappmvp.task.TaskAdapter;

import java.util.ArrayList;

public class TaskWidgetActivity extends Activity
{

    private static final String PREFS_NAME = "com.example.adria.myappmvp.widget.TaskWidgetActivity";
    private static final String PREF_TITLE = "Title";
    private static final String PREF_DESCRIPTION = "Description";


    private GridView mGridView;
    private TaskRepository mTaskRepository;
    private TaskAdapter mTaskAdapter;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_task_menu);

        setResult(RESULT_CANCELED);

        mGridView = findViewById(R.id.WidgetTaskGridView);
        mTaskRepository = mTaskRepository.getINSTANCE(getApplication());
        mTaskAdapter = new TaskAdapter(new ArrayList<Task>(0) );
        mGridView.setAdapter(mTaskAdapter);
        mTaskAdapter.replaceTaskList(mTaskRepository.getTasksList());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if(mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Task task = mTaskAdapter.getItem(i);
                String title = task.getTitle();
                String description = task.getDescription();
                String id = task.getId();

                Context context = TaskWidgetActivity.this;
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                TaskWidget.updateAppWidget(context,appWidgetManager,mAppWidgetId,title,description,id);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK,resultValue);
                finish();



            }
        });

    }

    static void saveTask(Context context, int appwidgetId, Task task)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME,0).edit();
        prefs.putString(PREF_TITLE, task.getTitle());
        prefs.putString(PREF_DESCRIPTION, task.getDescription());
        prefs.commit();
    }

    static String loadTitlePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        String title = prefs.getString(PREF_TITLE +appWidgetId,null);
        if(title != null)
        {
            return title;
        }
        else
        {
            return "";
        }
    }

    static String loadDescPref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String description = prefs.getString(PREF_DESCRIPTION + appWidgetId, null);
        if(description != null)
        {
            return description;
        }
        else
        {
            return "";
        }
    }
}
