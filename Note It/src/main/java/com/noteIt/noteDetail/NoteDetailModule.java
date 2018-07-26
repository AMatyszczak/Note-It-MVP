package com.noteIt.noteDetail;

import android.support.annotation.Nullable;

import com.noteIt.daggerInjections.ActivityScoped;
import com.noteIt.daggerInjections.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NoteDetailModule
{
    @FragmentScoped
    @ContributesAndroidInjector
    abstract NoteDetailFragment noteDetailFragment();

    @ActivityScoped
    @Binds
    abstract NoteDetailContract.Presenter noteDetailPresenter(NoteDetailPresenter noteDetailPresenter);

    @Provides
    @ActivityScoped
    static String provideNoteId(NoteDetailActivity activity) {
        return activity.getIntent().getStringExtra(NoteDetailActivity.GET_NOTE_DETAIL);
    }



}
