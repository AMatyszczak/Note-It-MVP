package com.noteIt.notes;


import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.noteIt.Injection;
import com.noteIt.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NoteFragTest
{

    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DESCRITPION2 = "DESCRIPTION2";

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
    public void editTask()
    {
        createTask(TITLE,DESCRIPTION);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.edit_note_title)).check(matches(isDisplayed()));


        onView(withId(R.id.edit_note_description)).perform(clearText()).perform(typeText(DESCRITPION2));
        onView(withId(R.id.fab_note_detail)).perform(click());

        onView(withText(DESCRITPION2)).check(matches(isDisplayed()));

    }

    private void createTask(String title, String descripion)
    {
        onView(withId(R.id.fab_note)).perform(click());
        onView(withId(R.id.add_note_title)).perform(typeText(title));
        onView(withId(R.id.add_note_description)).perform(typeText(descripion));
        onView(withId(R.id.fab_add_note)).perform(click());
    }

}
