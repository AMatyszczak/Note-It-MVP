package com.noteIt.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import static android.content.ContentValues.TAG;

public class MyWidgetRemoteViewsService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
//        String mRecipeId = intent.getData().getSchemeSpecificPart();
//        Log.e(TAG, "onGetViewFactory: " + mRecipeId );
        return new MyRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}
