package com.noteIt.ArchivedNotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noteIt.R;
import com.noteIt.RecyclerViewClasses.RecyclerNoteListAdapter;
import com.noteIt.data.Note;
import com.noteIt.data.Task;

import java.util.ArrayList;
import java.util.List;

public class ArchivedNotesFragment extends Fragment implements ArchivedNotesContract.View
{
    private RecyclerView mRecyclerView;
    private RecyclerNoteListAdapter mAdapter;
    private ArchivedNotesContract.Presenter mPresenter;

    private LinearLayout mEmptyLayout;
    private LinearLayout mNoteLayout;
    private TextView mEmptyTextView;

    @Override
    public void onResume() {
        if(mPresenter!= null)
            mPresenter.refreshNoteList();

        super.onResume();
    }

    @Override
    public void setPresenter(ArchivedNotesContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerNoteListAdapter(getContext(), new ArrayList<Note>(0), true, this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.archived_frag, container, false);

        mEmptyLayout = root.findViewById(R.id.archived_empty_note_layout);
        mNoteLayout = root.findViewById(R.id.archived_note_layout);
        mEmptyTextView = root.findViewById(R.id.archived_empty_note_textView);

        mRecyclerView = root.findViewById(R.id.archived_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        showEmptyView(false);


        return root;

    }

    @Override
    public void updateNoteList(List<Note> noteList) {
        mAdapter.replaceNoteList(noteList);
    }

    public ArrayList<Note> getArchivedNotes()
    {
        return mPresenter.getNotes();
    }

    public void deleteNotes(ArrayList<Note> noteList)
    {
        mPresenter.deleteNotes(noteList);
    }

    public void updateNotes(ArrayList<Note> noteList)
    {
        mPresenter.updateNotes(noteList);
    }

    public ArrayList<Task> getNoteTasks(String noteId)
    {
        return mPresenter.getNoteTasks(noteId);
    }


    public void showEmptyView(boolean show)
    {
        if(show)
        {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.VISIBLE);
            mNoteLayout.setVisibility(View.INVISIBLE);
        }
        else
        {
            mEmptyTextView.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
            mNoteLayout.setVisibility(View.VISIBLE);
        }
    }
}
