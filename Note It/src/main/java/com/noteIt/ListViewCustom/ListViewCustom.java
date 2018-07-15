package com.noteIt.ListViewCustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ListView;

import com.noteIt.TaskList.TaskRecyclerAdapter;
import com.noteIt.data.Task;

public class ListViewCustom extends ListView {
    public ListViewCustom(Context context) {
        super(context);
        init();
    }

    public ListViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            Task task = new Task("", false, String.valueOf(-1));
            TaskRecyclerAdapter taskRecyclerAdapter = (TaskRecyclerAdapter) getAdapter();
            taskRecyclerAdapter.addTask(task);
            this.requestFocus();

            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
