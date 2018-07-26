package com.noteIt.daggerInjections;


import com.noteIt.archivedNotes.ArchivedNoteActivity;
import com.noteIt.archivedNotes.ArchivedNoteModule;
import com.noteIt.noteAdd.NoteAddActivity;
import com.noteIt.noteAdd.NoteAddModule;
import com.noteIt.noteDetail.NoteDetailActivity;
import com.noteIt.noteDetail.NoteDetailModule;
import com.noteIt.notes.NoteActivity;
import com.noteIt.notes.NoteModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule
{

    @ActivityScoped
    @ContributesAndroidInjector(modules = NoteModule.class)
    abstract NoteActivity noteActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = NoteAddModule.class)
    abstract NoteAddActivity noteAddActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = NoteDetailModule.class)
    abstract NoteDetailActivity noteDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ArchivedNoteModule.class)
    abstract ArchivedNoteActivity archivedNotesActivity();

}
