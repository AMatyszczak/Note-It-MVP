package com.noteIt.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.noteIt.R;
import com.noteIt.data.Note;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.noteDetail.NoteDetailActivity;

import javax.inject.Inject;

public class NoteWidgetProvider extends AppWidgetProvider
{

    private static final int MIN_WIDGET_HEIGHT = 100;
    private static final int MIN_WIDGET_WIDTH = 70;

    private static final String NOTE_ID = "NOTE_ID";
    @Inject
    NoteRepository mNoteRepository;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE))
        {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,NoteWidgetProvider.class);
            int appWidgetIds[] = appWidgetManager.getAppWidgetIds(componentName);
            String Id;
            final int N = appWidgetIds.length;
            for(int i = 0; i < N; i++)
            {
                int appWidgetId = appWidgetIds[i];
                Id = NoteWidgetActivity.loadIdPref(context,appWidgetId);
                Note note = mNoteRepository.getNoteFromId(Id);

                if(note != null)
                {
                    NoteWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId, note.getTitle(), note.getDescription(), note.getId());
                }
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i: appWidgetIds) {
            int appWidgetId = appWidgetIds[i];

            String title = NoteWidgetActivity.loadTitlePref(context,appWidgetId);
            String description = NoteWidgetActivity.loadDescPref(context,appWidgetId);
            String id = NoteWidgetActivity.loadIdPref(context,appWidgetId);

            updateAppWidget(context, appWidgetManager, appWidgetId, title,description, id);

        }
    }



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String title, String description, String noteId)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        views.setTextViewText(R.id.widgetTitle,title);
        views.setTextViewText(R.id.widgetDescription,description);

        Intent intentActivity = new Intent(context, NoteDetailActivity.class);
        intentActivity.putExtra(NoteDetailActivity.GET_NOTE_DETAIL, noteId);
        intentActivity.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
        views.setPendingIntentTemplate(R.id.widgetTaskList,pendingIntent);

        Intent intentTask = new Intent(context, MyWidgetRemoteViewsService.class);
        intentTask.putExtra(NOTE_ID, noteId);
        intentTask.setData(Uri.fromParts("content",noteId,null));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetTaskList);
        views.setRemoteAdapter(R.id.widgetTaskList, intentTask);


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
