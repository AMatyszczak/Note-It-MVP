package com.example.adria.myappmvp.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Entity(tableName="task")
public final class Task
{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @NonNull
    @ForeignKey(entity = Note.class,parentColumns = "id", childColumns = "noteId")
    @ColumnInfo(name = "noteId")
    private String mNoteId;


    public Task(String description, String noteId)
    {
        this(UUID.randomUUID().toString(), description, noteId);

    }

    @Ignore
    Task(String id, String description, String noteId)
    {
        this.mId = id;
        this.mDescription = description;
        this.mNoteId = noteId;
    }



    @Nonnull
    public String getId() {
        return mId;
    }

    public void setId(@Nonnull String mId) {
        this.mId = mId;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String mDescription) {
        this.mDescription = mDescription;
    }

    @Nonnull
    public String getNoteId() {
        return mNoteId;
    }

    public void setNoteId(@Nonnull String mNoteId) {
        this.mNoteId = mNoteId;
    }
}
