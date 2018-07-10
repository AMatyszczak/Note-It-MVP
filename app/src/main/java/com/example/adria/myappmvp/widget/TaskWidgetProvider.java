package com.example.adria.myappmvp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;
import com.example.adria.myappmvp.data.local.TaskRepository;
import com.example.adria.myappmvp.task.TaskActivity;

import static android.content.ContentValues.TAG;

public class TaskWidgetProvider extends AppWidgetProvider
{

    private static final int MIN_WIDGET_HEIGHT = 100;
    private static final int MIN_WIDGET_WIDTH = 70;
    TaskRepository mTaskRepository;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE))
        {
            mTaskRepository = TaskRepository.getINSTANCE(context);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,TaskWidgetProvider.class);
            int appWidgetIds[] = appWidgetManager.getAppWidgetIds(componentName);

            String Id;
            final int N = appWidgetIds.length;
            for(int i = 0; i < N; i++)
            {
                int appWidgetId = appWidgetIds[i];
                Id = TaskWidgetActivity.loadIdPref(context,appWidgetId);
                Task task = mTaskRepository.getTaskFromId(Id);

                if(task != null)
                {
                    TaskWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId, task.getTitle(), task.getDescription(), task.getId());
                }
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.e(TAG, "onUpdate: !!!!" );
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            String title = TaskWidgetActivity.loadTitlePref(context,appWidgetId);
            String description = TaskWidgetActivity.loadDescPref(context,appWidgetId);
            String id = TaskWidgetActivity.loadIdPref(context,appWidgetId);

            updateAppWidget(context, appWidgetManager, appWidgetId, title,description,id);

        }
    }



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String title, String description, String taskId)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);


        views.setTextViewText(R.id.widgetTitle,title);
        views.setTextViewText(R.id.widgetDescription,description);
        Intent intent = new Intent(context, TaskActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
        views.setInt(R.id.widgetLayout, "setBackgroundResource", R.drawable.widget_style);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        setWidgetTitle(context, appWidgetManager, appWidgetId);
    }

    private void setWidgetTitle(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if(bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) < MIN_WIDGET_HEIGHT &&
                bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH) < MIN_WIDGET_WIDTH)
        {
            views.setViewVisibility(R.id.widgetTitle,View.GONE);
        }
        else
        {
            views.setViewVisibility(R.id.widgetTitle,View.VISIBLE);
        }
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }
}