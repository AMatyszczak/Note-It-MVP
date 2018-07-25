package com.noteIt;

import com.noteIt.daggerInjections.DaggerAppComponent;
import com.noteIt.data.local.NoteRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.HasActivityInjector;

public class NoteApplication extends DaggerApplication implements HasActivityInjector
{
    @Inject
    NoteRepository mNoteRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        return DaggerAppComponent.builder().application(this).build();
    }
}
