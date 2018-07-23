package com.noteIt.ArchivedNotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.noteIt.R;
import com.noteIt.RecyclerViewClasses.RecyclerNoteListAdapter;
import com.noteIt.data.Note;
import com.noteIt.notes.NoteContract;

import java.util.ArrayList;
import java.util.List;

public class ArchivedNotesFragment extends Fragment implements ArchivedNotesContract.View
{
    RecyclerView mRecyclerView;
    RecyclerNoteListAdapter mAdapter;
    ArchivedNotesContract.Presenter mPresenter;

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
        mAdapter = new RecyclerNoteListAdapter(getContext(), new ArrayList<Note>(0), true);
        mPresenter.refreshNoteList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.archived_frag, container, false);
        mRecyclerView = root.findViewById(R.id.archived_recylerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        return root;

    }

    @Override
    public void updateNoteList(List<Note> noteList) {
        mAdapter.replaceNoteList(noteList);
    }
}
