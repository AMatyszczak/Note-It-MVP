package com.noteIt.archivedNotes;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.daggerInjections.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ArchivedNoteModule
{
    @ContributesAndroidInjector
    @FragmentScoped
    abstract ArchivedNoteFragment archivedNotesFragment();

    @ActivityScoped
    @Binds
    abstract ArchivedNoteContract.Presenter archivedNotesPresenter(ArchivedNotePresenter archivedNotePresenter);

}
