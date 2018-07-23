package com.noteIt.ArchivedNotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noteIt.R;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;
import com.noteIt.noteDetail.NoteDetailActivity;

import java.util.ArrayList;

public class ArchivedRecyclerViewAdapter
{
//
//    private ArrayList<Note> mArrayList;
//    private Context mContext;
//    private NoteRepository mNoteRepository;
//    private ActionMode mActionMode;
//
//    private ArrayList<CardView> mSelectedCardViews;
//    private ArrayList<Note> mSelectedNotes;
//
//    class MyViewHolder extends RecyclerView.ViewHolder
//    {
//        private CardView mCardView;
//        private TextView mTitle;
//        private TextView mDescription;
//        private LinearLayout mTaskLayout;
//
//        MyViewHolder(View v) {
//            super(v);
//            mTitle = v.findViewById(R.id.note_title);
//            mDescription = v.findViewById(R.id.note_description);
//            mCardView = v.findViewById(R.id.noteCardView);
//            mTaskLayout = v.findViewById(R.id.note_task_layout);
//
//        }
//
//        void update(Note note)
//        {
//            mTitle.setText(note.getTitle());
//            mDescription.setText(note.getDescription());
//            setOnClickIntent(note);
//            updateTasks(note.getId());
//            setOnLongClickActionMode(note);
//
//
//        }
//
//        void setOnClickIntent(final Note note)
//        {
//            mCardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(mActionMode != null)
//                    {
//                        mCardView.setSelected(!mCardView.isSelected());
//                        if(mCardView.isSelected())
//                        {
//                            mSelectedCardViews.add(mCardView);
//                            mSelectedNotes.add(note);
//                        }
//                        else
//                        {
//                            mSelectedCardViews.remove(mCardView);
//                            mSelectedNotes.remove(note);
//                        }
//
//                        setActionModeTitle(mSelectedCardViews.size());
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(mContext, NoteDetailActivity.class);
//                        intent.putExtra(NoteDetailActivity.GET_NOTE_DETAIL, note.getId());
//                        mContext.startActivity(intent);
//                    }
//
//                }
//            });
//        }
//
//        void setOnLongClickActionMode(final Note note)
//        {
//            mCardView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    if(mActionMode == null)
//                    {
//                        mActionMode = ((AppCompatActivity)mContext).startSupportActionMode(actionModeCallbacks);
//                        mCardView.setSelected(true);
//                        mSelectedCardViews.add(mCardView);
//                        mSelectedNotes.add(note);
//                        setActionModeTitle(1);
//                    }
//                    else
//                    {
//                        mActionMode.finish();
//                    }
//                    return true;
//                }
//            });
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//        private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
//
//            @Override
//            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                MenuInflater inflater = actionMode.getMenuInflater();
//                inflater.inflate(R.menu.contextual_menu, menu);
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.item_delete:
//                        mArrayList.removeAll(mSelectedNotes);
//                        mNoteRepository.deleteNotes(mSelectedNotes);
//
//                        actionMode.finish();
//                        break;
//                    case R.id.item_archive:
//                        mSelectedNotes = archiveNotes(mSelectedNotes);
//                        mNoteRepository.updateNotes(mSelectedNotes);
//                        actionMode.finish();
//                        break;
//                }
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                for (CardView cardview: mSelectedCardViews) {
//                    cardview.setSelected(false);
//                }
//                if(isArchived)
//                    setList(mNoteRepository.getArchivedNotes());
//                else
//                    setList(mNoteRepository.getNotesList());
//                mSelectedCardViews.clear();
//                mSelectedNotes.clear();
//
//                mActionMode = null;
//            }
//
//            void removeNotesFromArchived(ArrayList<Note> noteList){
//                for (Note note: noteList) {
//                    note.setArchived(note.isArchived());
//
//                }
//            }
//
//            ArrayList<Note> archiveNotes(ArrayList<Note> noteList)
//            {
//                for (Note note: noteList) {
//                    note.setArchived(!note.isArchived());
//
//                }
//                return noteList;
//            }
//
//        };
}
