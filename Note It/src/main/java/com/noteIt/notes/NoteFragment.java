package com.noteIt.notes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noteIt.RecyclerViewClasses.ItemTouchHelperCallback;
import com.noteIt.RecyclerViewClasses.OnStartDrag;
import com.noteIt.RecyclerViewClasses.RecyclerNoteListAdapter;
import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.noteAdd.NoteAddActivity;
import com.noteIt.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
@ActivityScoped
public class NoteFragment extends Fragment implements NoteContract.View, OnStartDrag {

    public final static int ADD_NOTE = 1;

    public final static int NOTE_DETAIL_REQUEST = 2;
    public final static String NOTE_SAVED_SNACKBAR_TEXT = "Note Saved";

    private RecyclerNoteListAdapter mRecyclerNoteListAdapter;

    @Inject
    public NoteContract.Presenter mPresenter;

    private RecyclerView mNoteRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    private LinearLayout mNoteLayout;

    private TextView mNoNoteTextView;
    private LinearLayout mNoNoteLayout;

    @Inject
    public NoteFragment() {

    }

    @Override
    public void onResume() {
        if(mPresenter!= null)
            updateNoteList(mPresenter.getNotes());

        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerNoteListAdapter = new RecyclerNoteListAdapter(getContext(), new ArrayList<Note>(0), false, this);
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.note_frag, container, false);
        mNoteLayout = root.findViewById(R.id.note_layout);
        mNoNoteTextView = root.findViewById(R.id.empty_note_textView);
        mNoNoteLayout = root.findViewById(R.id.empty_note_layout);

        mNoteRecyclerView = root.findViewById(R.id.note_recyclerView);
        mNoteRecyclerView.setHasFixedSize(true);
        mNoteRecyclerView.setAdapter(mRecyclerNoteListAdapter);
        mNoteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(mRecyclerNoteListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mNoteRecyclerView);

        showEmptyView(true);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        FloatingActionButton fab = getActivity().findViewById(R.id.fab_note);

            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNoteStart();
            }
        });
        if(mPresenter!= null)
            updateNoteList(mPresenter.getNotes());
    }


    public void updateNoteList(List<Note> noteList) {
        mRecyclerNoteListAdapter.replaceNoteList(noteList);
    }

    @Override
    public ArrayList<Task> getNoteTasks(String noteId) {
            return mPresenter.getNoteTasks(noteId);
    }


    @Override
    public void addNoteStart() {
        Intent intent = new Intent(getActivity(), NoteAddActivity.class);
        startActivityForResult(intent, ADD_NOTE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == NOTE_DETAIL_REQUEST)
            if(resultCode == Activity.RESULT_OK)
            {
                showSnackBar( NOTE_SAVED_SNACKBAR_TEXT );
                mRecyclerNoteListAdapter.replaceNoteList(mPresenter.getNotes());
            }

    }

    public void showEmptyView(boolean show) {
        if (show) {
            mNoteRecyclerView.setVisibility(View.GONE);
            mNoteLayout.setVisibility(View.GONE);

            mNoNoteTextView.setVisibility(View.VISIBLE);
            mNoNoteLayout.setVisibility(View.VISIBLE);
        } else {
            mNoteRecyclerView.setVisibility(View.VISIBLE);
            mNoteLayout.setVisibility(View.VISIBLE);

            mNoNoteTextView.setVisibility(View.GONE);
            mNoNoteLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showSnackBar(String text)
    {
        if(getView()!= null)
            Snackbar.make(getView(),text,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    public void deleteNotes(ArrayList<Note> noteList)
    {
        mPresenter.deleteNotes(noteList);
    }

    public void updateNotes(ArrayList<Note> noteList)
    {
        mPresenter.updateNotes(noteList);
    }

    public ArrayList<Note> getNotes()
    {
        return (ArrayList<Note>)mPresenter.getNotes();
    }


}

