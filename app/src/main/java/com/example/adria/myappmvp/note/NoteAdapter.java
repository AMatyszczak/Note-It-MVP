package com.example.adria.myappmvp.note;



import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter
{
    private List<Note> mNoteList;
    private NoteFragment mNoteFragment;

    public NoteAdapter(List<Note> noteList)
    {

        this.mNoteList = noteList;
        setList(noteList);
    }

    public void setFragment(Fragment fragment)
    {
        mNoteFragment = (NoteFragment)fragment;
    }

    @Override
    public int getCount() {
        return mNoteList.size();
    }

    @Override
    public Note getItem(int i)
    {
        return mNoteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = view;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            root = inflater.inflate(R.layout.note, viewGroup, false);
        }

        Note note = getItem(i);

        TextView title = root.findViewById(R.id.title);
        title.setText(note.getTitle());

        TextView description = root.findViewById(R.id.description);
        description.setText(note.getDescription());


        return root;
    }
    public void addNote(Note note)
    {
        mNoteList.add(note);
        notifyDataSetChanged();

    }

    public void removeNote(Note note)
    {
        mNoteList.remove(note);
        notifyDataSetChanged();

    }

    public void replaceNoteList(List<Note> noteList)
    {
        setList(noteList);
    }
    private void setList(List<Note> noteList)
    {
        mNoteList.clear();
        mNoteList.addAll(noteList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


    public void swapItems(int fromId, int toId)
    {
        Note temp = mNoteList.get(fromId);
        Note toIdNote = mNoteList.get(toId);

        mNoteList.set(fromId, toIdNote);
        mNoteList.set(toId, temp);

        if(mNoteFragment != null)
            mNoteFragment.notifyDataSwapped(temp, toIdNote);


    }

    public ArrayList<Note> getNotesFromIds(SparseBooleanArray notesIds)
    {
        ArrayList<Note> noteList = new ArrayList<>() ;

        for (int i = 0 ; i < notesIds.size(); i++)
        {
            if(notesIds.valueAt(i))
            {
                 noteList.add(mNoteList.get(notesIds.keyAt(i)));
            }

        }
        return noteList;
    }
}
