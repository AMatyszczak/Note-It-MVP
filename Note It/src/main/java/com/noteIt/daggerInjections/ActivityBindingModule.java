package com.noteIt.daggerInjections;


import com.noteIt.noteAdd.NoteAddActivity;
import com.noteIt.noteAdd.NoteAddModule;
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


}
