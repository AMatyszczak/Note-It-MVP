package com.noteIt.widget;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noteIt.R;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.notes.NoteFragment;
import com.noteIt.widget.NoteWidgetActivity;

import java.util.ArrayList;
import java.util.List;


public class WidgetNotesAdapter extends BaseAdapter {
    private List<Note> mNoteList;
    
    private NoteFragment mNoteFragment;
    private NoteWidgetActivity mWidgetActivity;
    private int N ;

    public WidgetNotesAdapter(List<Note> noteList) {

        N = 0;
        mNoteFragment = null;
        mWidgetActivity = null;
        this.mNoteList = noteList;
        setList(noteList);
    }

    public void setFragment(Fragment fragment) {
        mNoteFragment = (NoteFragment) fragment;
    }

    public void setWidgetActivity(Activity activity) {
        mWidgetActivity = (NoteWidgetActivity)activity;
    }

    @Override
    public int getCount() {
        return mNoteList.size();
    }

    @Override
    public Note getItem(int i) {
        return mNoteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Log.e(TAG, "getView: WYWOLANE!!!!!!!!!!!!!!!!!!!!!, N: " + N );
        N++;
        View root = view;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            root = inflater.inflate(R.layout.note, viewGroup, false);
        }

        Note note = getItem(i);

        TextView title = root.findViewById(R.id.note_title);
        title.setText(note.getTitle());

        TextView description = root.findViewById(R.id.add_note_description);
        description.setText(note.getDescription());

        LinearLayout linearLayout = root.findViewById(R.id.note_task_layout);
        ArrayList<Task> taskList = new ArrayList<>(0);

        if(mNoteFragment != null)
            taskList = mNoteFragment.getNoteTasks(note.getId());
        if(mWidgetActivity != null)
            taskList = mWidgetActivity.getNoteTasks(note.getId());
        linearLayout.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());


        for (Task task : taskList) {

            RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.task_fragment_layout, viewGroup, false);
            TextView textView = v.findViewById(R.id.task_layout_text);
            CheckBox checkBox = v.findViewById(R.id.task_layout_checkbox);
            textView.setText(task.getDescription());
            checkBox.setChecked(task.isDone());

            linearLayout.addView(v);

        }
        return root;
    }

    public void replaceNoteList(List<Note> noteList) {
        setList(noteList);
    }

    private void setList(List<Note> noteList) {
        mNoteList.clear();
        mNoteList.addAll(noteList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


}
