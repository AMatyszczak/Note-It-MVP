package com.example.adria.myappmvp.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.task.TaskActivity;
import com.example.adria.myappmvp.taskDetail.TaskDetailActivity;

import static android.content.ContentValues.TAG;

public class TaskWidget extends AppWidgetProvider
{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        final int N = appWidgetIds.length;
//
//        for (int i=0; i<N; i++) {
//            int appWidgetId = appWidgetIds[i];
//
//
//
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//
//        }
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

        appWidgetManager.updateAppWidget(appWidgetId, views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
