package com.noteIt.notes;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.gridViewCustom.GridViewCustom;
import com.noteIt.noteAdd.NoteAddActivity;
import com.noteIt.R;
import com.noteIt.noteDetail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteFragment extends Fragment implements NoteContract.View {

    private final static int ADD_NOTE = 1;
    private final static String GET_NOTE_DETAIL = "GETNOTEDETAIL";

    private NoteAdapter mNoteAdapter;
    private NoteContract.Presenter mPresenter;

    private GridViewCustom mNoteGridView;
    private LinearLayout mNoteLayout;

    private TextView mNoNoteTextView;
    private LinearLayout mNoNoteLayout;


    public NoteFragment() {

    }

    @Override
    public void onResume() {
        mPresenter.refreshNoteList();

        super.onResume();
    }

    @Override
    public void setPresenter(NoteContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteAdapter = new NoteAdapter(new ArrayList<Note>(0));
        mNoteAdapter.setFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.note_frag, container, false);
        mNoteLayout = root.findViewById(R.id.noteLayout);
        mNoNoteTextView = root.findViewById(R.id.noNoteTextView);
        mNoNoteLayout = root.findViewById(R.id.noNoteLayout);

        mNoteGridView = root.findViewById(R.id.noteGridView);
        mNoteGridView.setAdapter(mNoteAdapter);
        mNoteGridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mNoteGridView.setMultiChoiceModeListener(new MyMultiChoiceListener(mNoteAdapter));

        mNoteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getNoteDetail(i);
                Log.e(TAG, "onItemClick: ");
            }
        });
        showNoNoteMenu(false);

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
        mPresenter.refreshNoteList();
    }

    @Override
    public void updateNoteList(List<Note> noteList) {
        mNoteAdapter.replaceNoteList(noteList);
    }

    @Override
    public ArrayList<Task> getNoteTasks(String noteId) {
        return mPresenter.getNoteTasks(noteId);
    }


    @Override
    public void addNoteStart() {
        Intent intent = new Intent(getActivity(), NoteAddActivity.class);
        startActivityForResult(intent, ADD_NOTE);
        mNoteAdapter.replaceNoteList(mPresenter.getAllNotes());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mNoteAdapter.replaceNoteList(mPresenter.getAllNotes());
    }

    @Override
    public void showNoNoteMenu(boolean show) {
        if (show) {
            mNoteGridView.setVisibility(View.GONE);
            mNoteLayout.setVisibility(View.GONE);

            mNoNoteTextView.setVisibility(View.VISIBLE);
            mNoNoteLayout.setVisibility(View.VISIBLE);
        } else {
            mNoteGridView.setVisibility(View.VISIBLE);
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
    public void getNoteDetail(int noteFromList) {
        Note note = mNoteAdapter.getItem(noteFromList);
        Intent intent = new Intent(getContext(), NoteDetailActivity.class);
        intent.putExtra(GET_NOTE_DETAIL, note.getId());
        Log.e(TAG, "getNoteDetail: " + note.getId().toString());
        startActivity(intent);
    }

    public void notifyDataSwapped(Note fromNote, Note toNote) {
        mPresenter.swapNotesPositions(fromNote, toNote);
    }

    private class MyMultiChoiceListener implements AbsListView.MultiChoiceModeListener {

        NoteAdapter skAdapter;

        MyMultiChoiceListener(NoteAdapter noteAdapter) {
            skAdapter = noteAdapter;
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            ArrayList<Note> arrayList = skAdapter.getNotesFromIds(mNoteGridView.getCheckedItemPositions());
            switch (menuItem.getItemId()) {
                case R.id.item_delete:

                    mPresenter.deleteNotes(arrayList);
                    actionMode.finish();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            int checkedItemCountNote = mNoteGridView.getCheckedItemCount();
            actionMode.setTitle(checkedItemCountNote + " Selected");

        }
    }

}

