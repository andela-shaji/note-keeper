package com.checkpoint.andela.notekeeper.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ImageButton;

import com.checkpoint.andela.notekeeper.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateAndListNotesTest {
    @Rule
    public ActivityTestRule rule1 = new ActivityTestRule(ListNotes.class);

    @Test
    public void testDisplayNote() {
        Espresso.onView(withText("Suada")).check((doesNotExist()));
        Espresso.onView(withText("Tosin")).check(matches(isDisplayed()));
    }

    @Test
    public void testAddNote() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_list)).perform(click());
        onView(withId(R.id.note_title)).perform(typeText("Eston"));
        onView(withId(R.id.note_content)).perform(typeText("Is a fellow at andela."));
        onView(allOf(instanceOf(ImageButton.class), withParent(withId(R.id.create_note_toolbar)))).perform(click());
        onView(withText("Eston")).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteNote() throws Exception{
        Espresso.onView(ViewMatchers.withId(R.id.fab_list)).perform(click());
        onView(withId(R.id.note_title)).perform(typeText("Kenya"));
        onView(withId(R.id.note_content)).perform(typeText("This is a lovely country."));
        onView(allOf(instanceOf(ImageButton.class), withParent(withId(R.id.create_note_toolbar)))).perform(click());
        Espresso.onView(withText("Kenya")).perform(longClick());
        onView(withId(R.id.nav_trash)).perform(click());
       Espresso.onView(withText("Kenya")).check((doesNotExist()));
    }

    @Test
    public void testEditNote() throws Exception{
        Espresso.onView(ViewMatchers.withId(R.id.fab_list)).perform(click());
        onView(withId(R.id.note_title)).perform(typeText("Android"));
        onView(withId(R.id.note_content)).perform(typeText("Testing target devices"));
        onView(allOf(instanceOf(ImageButton.class), withParent(withId(R.id.create_note_toolbar)))).perform(click());
        Espresso.onView(withText("Android")).perform(longClick());
        onView(withId(R.id.nav_edit)).perform(click());
        onView(withId(R.id.note_content)).perform(typeText("\n Using JUnit 4."));
        onView(allOf(instanceOf(ImageButton.class), withParent(withId(R.id.create_note_toolbar)))).perform(click());
    }
}