package com.noteIt.RecyclerViewClasses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.noteIt.R;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.noteDetail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerNoteListAdapter extends RecyclerView.Adapter<RecyclerNoteListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Note> mArrayList;
    private Context mContext;
    private NoteRepository mNoteRepository;
    private ActionMode mActionMode;

    private ArrayList<CardView> mSelectedCardViews;
    private ArrayList<Note> mSelectedNotes;

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView mCardView;
        private TextView mTitle;
        private TextView mDescription;
        private LinearLayout mTaskLayout;

        ViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.note_title);
            mDescription = v.findViewById(R.id.note_description);
            mCardView = v.findViewById(R.id.noteCardView);
            mTaskLayout = v.findViewById(R.id.note_task_layout);

        }

        void update(Note note)
        {
            mTitle.setText(note.getTitle());
            mDescription.setText(note.getDescription());
            setOnClickIntent(note);
            updateTasks(note.getId());
            setOnLongClickActionMode(note);


        }

        void setOnClickIntent(final Note note)
        {
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mActionMode != null)
                    {
                        mCardView.setSelected(!mCardView.isSelected());
                        if(mCardView.isSelected())
                        {
                            mSelectedCardViews.add(mCardView);
                            mSelectedNotes.add(note);
                        }
                        else
                        {
                            mSelectedCardViews.remove(mCardView);
                            mSelectedNotes.remove(note);
                        }

                        setActionModeTitle(mSelectedCardViews.size());
                    }
                    else
                    {
                        Intent intent = new Intent(mContext, NoteDetailActivity.class);
                        intent.putExtra(NoteDetailActivity.GET_NOTE_DETAIL, note.getId());
                        mContext.startActivity(intent);
                    }

                }
            });
        }

        void setOnLongClickActionMode(final Note note)
        {
            mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(mActionMode == null)
                    {
                        mActionMode = ((AppCompatActivity)mContext).startSupportActionMode(actionModeCallbacks);
                        mCardView.setSelected(true);
                        mSelectedCardViews.add(mCardView);
                        mSelectedNotes.add(note);
                        setActionModeTitle(1);
                    }
                    return true;
                }
            });
        }

        void updateTasks(String noteId)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ArrayList<Task> tasks =  mNoteRepository.getNoteTasks(noteId);
            mTaskLayout.removeAllViews();
            for (Task task: tasks) {
                try
                {
                    View view = inflater.inflate(R.layout.task_fragment_layout, mTaskLayout, false);
                    TextView textView = view.findViewById(R.id.task_layout_text);
                    CheckBox checkBox = view.findViewById(R.id.task_layout_checkbox);
                    textView.setText(task.getDescription());
                    checkBox.setChecked(task.isDone());
                    mTaskLayout.addView(view);
                } catch (NullPointerException e) {
                    Log.e(TAG, "updateTasks: NPE " + e.getMessage());
                }


            }
        }
    }

    private void setActionModeTitle(int ArraySize)
    {
        if(ArraySize < 1)
        {
            mActionMode.finish();
        }
        else
        {
            mActionMode.setTitle("Selected: " + mSelectedCardViews.size());
        }

    }

    public RecyclerNoteListAdapter(Context context, ArrayList<Note> items) {
        this.mArrayList = items;
        this.mContext = context;
        this.mNoteRepository = NoteRepository.getINSTANCE(mContext);
        mSelectedCardViews = new ArrayList<>(0);
        mSelectedNotes = new ArrayList<>(0);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_delete:
                    mArrayList.removeAll(mSelectedNotes);
                    mNoteRepository.deleteNotes(mSelectedNotes);
                    notifyDataSetChanged();
                    actionMode.finish();

                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (CardView cardview: mSelectedCardViews) {
                cardview.setSelected(false);
            }
            mSelectedCardViews.clear();
            mActionMode = null;
        }

    };
}