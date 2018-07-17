package com.noteIt.notes;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.noteIt.Injection;
import com.noteIt.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.internal.util.Checks.checkArgument;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NoteFragTest
{

    private static final String TITLE = "TITLE";
    private static final String TITLE2 = "TITLE2";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DESCRIPTION2 = "DESCRIPTION2";

    private static final String TASKDESC = "TASKDESC";
    private static final String TASKDESC2 = "TASKDESC2";
    private static final String TASKDESC3 = "TASKDESC3";

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTaskRule = new ActivityTestRule<NoteActivity>(NoteActivity.class)
    {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            Injection.provideNoteRepository(InstrumentationRegistry.getTargetContext()).deleteAllNotes();
        }
    };


    @Test
    public void editNote()
    {
        createNote(TITLE,DESCRIPTION, "", TASKDESC2);
        onView(withText(TITLE)).perform(click());
        onView(withId(R.id.detail_note_title)).check(matches(isDisplayed()));

        onView(withId(R.id.detail_note_description)).perform(clearText()).perform(typeText(DESCRIPTION2));
        onView(withId(R.id.detail_note_title)).perform(clearText()).perform(typeText(TITLE2));

        onView(allOf(isDisplayed(),withId(R.id.task_recycler_view_detail)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,typeText(TASKDESC3)));
        onView(allOf(isDisplayed(), withId(R.id.task_clear_button), hasSibling(withText(TASKDESC2)))).perform(click());

        clickAddNewTask();
        onView(allOf(isDisplayed(), withId(R.id.task_recycler_view_detail)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,typeText(TASKDESC) ));
        clickCheckBoxForTask(TASKDESC);

        onView(withId(R.id.fab_note_detail)).perform(click());

        onView(withText(DESCRIPTION2)).check(matches(isDisplayed()));
        onView(withText(TITLE2)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.task_layout_text), withText(TASKDESC3))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.task_layout_text), withText(TASKDESC2))).check(doesNotExist());
        onView(allOf(withId(R.id.task_layout_text), withText(TASKDESC))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.task_layout_checkbox), hasSibling(withText(TASKDESC)))).check(matches(isChecked()));
    }

    private void clickCheckBoxForTask(String desc) {
        onView(Matchers.allOf(withId(R.id.task_checkbox), hasSibling(withText(desc)))).perform(click());
    }

    private void clickAddNewTask()
    {
        onView(withId(R.id.add_task_text_view)).perform(click());
    }

    private void createNote(String title, String description, String... taskDesc)
    {
        onView(withId(R.id.fab_note)).perform(click());
        onView(withId(R.id.add_note_title)).perform(typeText(title));
        onView(withId(R.id.add_note_description)).perform(typeText(description));

        for(int i = 0 ; i < taskDesc.length; i++)
        {
            clickAddNewTask();
            onView(allOf(isDisplayed(), withId(R.id.task_recycler_view_add)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(i, typeText(taskDesc[i])));
        }

        onView(withId(R.id.fab_add_note)).perform(click());


    }

}
