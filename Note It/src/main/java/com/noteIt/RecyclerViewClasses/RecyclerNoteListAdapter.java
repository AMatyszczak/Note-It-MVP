package com.noteIt.RecyclerViewClasses;

import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.noteIt.R;
import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerNoteListAdapter extends RecyclerView.Adapter<RecyclerNoteListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Note> mArrayList;



    class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mTitle;
        public TextView mDescription;
        public LinearLayout mTaskLayout;

        public ViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.note_title);
            mDescription = v.findViewById(R.id.note_description);
            mTaskLayout = v.findViewById(R.id.note_task_layout);

        }

        void update(Note note)
        {
            mTitle.setText(note.getTitle());
            mDescription.setText(note.getDescription());

        }

    }

    public RecyclerNoteListAdapter(ArrayList<Note> items) {
        this.mArrayList = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View root = layoutInflater.inflate(R.layout.note, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(root);
        root.setTag(viewHolder);

        return (ViewHolder)root.getTag();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(mArrayList.get(position));

    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mArrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void replaceNoteList(List<Note> noteList) {
        setList(noteList);
    }


    private void setList(List<Note> noteList) {
        mArrayList.clear();
        mArrayList.addAll(noteList);
        notifyDataSetChanged();
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {

        private ArrayList<String> mSelectedItems;

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };
}