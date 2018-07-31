package com.noteIt.noteAdd;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.daggerInjections.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NoteAddModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract NoteAddFragment noteAddFragment();

    @ActivityScoped
    @Binds
    abstract NoteAddContract.Presenter noteAddPresenter(NoteAddPresenter noteAddPresenter);

    @Provides
    static NoteAddFragment noteaddFragment()
    {
        return new NoteAddFragment();
    }
}
