package com.noteIt.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.noteIt.data.Note;
import com.noteIt.data.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest
{

    private static final String NOTE_ID = "noteId";
    private static final Task TASK = new Task("description", false, NOTE_ID);
    private static final Task TASK2 = new Task("description2", true, NOTE_ID);

    private LocalAppDatabase mAppDatabase;
    private TaskDao mTaskDao;

    @Before
    public void init()
    {
        mAppDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), LocalAppDatabase.class).build();
        mTaskDao = mAppDatabase.taskDao();
    }

    @After
    public void closeDb()
    {
        mAppDatabase.close();
    }

    @Test
    public void insert_getTask()
    {
        mTaskDao.insertTask(TASK);

        Task task = mTaskDao.getTaskFromId(TASK.getId());

        checkTask(task, TASK.getId(), TASK.getDescription(), TASK.isDone());
    }

    @Test
    public void insertOnConflict_update_getTask()
    {
        mTaskDao.insertTask(TASK);
        TASK.setDescription("DESC");
        mTaskDao.updateTask(TASK);
        Task task = mTaskDao.getTaskFromId(TASK.getId());

        checkTask(task, TASK.getId(), TASK.getDescription(),TASK.isDone());
    }

    @Test
    public void insert_getTasks()
    {
        NoteDao noteDao = mAppDatabase.noteDao();
        Note note = new Note(NOTE_ID,"title","description",1);
        noteDao.insertNote(note);

        ArrayList<Task> taskList = new ArrayList<>(0);
        taskList.add(TASK);
        taskList.add(TASK2);

        mTaskDao.insertTasks(taskList);
        ArrayList<Task> tasksReturned = (ArrayList<Task>)mTaskDao.getNoteTasks(TASK.getNoteId());

        checkTask(tasksReturned.get(0), TASK.getId(), TASK.getDescription(),TASK.isDone());
        checkTask(tasksReturned.get(1), TASK2.getId(), TASK2.getDescription(),TASK2.isDone());
    }



    private void checkTask(Task task,String id, String description, Boolean isDone)
    {
        assertThat(task, notNullValue());
        assertThat(task.getId(), is(id));
        assertThat(task.getDescription(), is(description));
        assertThat(task.isDone(), is(isDone));
    }

}
