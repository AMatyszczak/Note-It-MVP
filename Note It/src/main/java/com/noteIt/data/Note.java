package com.noteIt.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

import javax.annotation.Nonnull;

/**
 * Created by adria on 23.04.2018.
 */
@Entity(tableName = "note")
public final class Note {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @Nullable
    @ColumnInfo(name = "position")
    private int mPosition;

    @NonNull
    @ColumnInfo(name = "archived")
    private boolean isArchived;

    public Note(@Nullable String title, @Nullable String description, @Nullable int position) {
        this(UUID.randomUUID().toString(), title, description, position, false);
    }

    @Ignore
    public Note(@Nullable String title, @Nullable String description, @Nullable int position, boolean isArchived) {
        this(UUID.randomUUID().toString(), title, description, position, isArchived);
    }

    @Ignore
    public Note(@NonNull String Id, @Nullable String title, @Nullable String description, @Nullable int position, boolean isArchived) {
        mId = Id;
        mTitle = title;
        mDescription = description;

        mPosition = position;
    }


    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(String Id) {
        mId = Id;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @NonNull
    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(@NonNull boolean archived) {
        isArchived = archived;
    }

    public void subtractPosition(int value) { setPosition(this.mPosition -= value); }

    public void addToPosition(int value) { setPosition(this.mPosition += value); }


}
