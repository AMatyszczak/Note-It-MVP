package com.noteIt.notes;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.daggerInjections.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NoteModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract NoteFragment noteFragment();

    @ActivityScoped
    @Binds
    abstract NoteContract.Presenter notePresenter(NotePresenter presenter);

    @Provides
    static NoteFragment notefragment()
    {
        return new NoteFragment();
    }
}
