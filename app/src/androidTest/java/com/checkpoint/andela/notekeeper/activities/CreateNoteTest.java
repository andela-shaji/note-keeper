package com.checkpoint.andela.notekeeper.activities;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ImageButton;

import com.checkpoint.andela.notekeeper.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateNoteTest {



    @Rule
    public ActivityTestRule rule
            = new ActivityTestRule<>(CreateNote.class);

    @Test
    public void testCreateNote() throws Exception {
        Intents.init();
        String title = "Note Keeper";
        String content = "It is a new, comfortable and simple notepad right on your Android.";
        onView(withId(R.id.note_title)).perform(typeText(title)).check(matches(withText(title)));
        onView(withId(R.id.note_content)).perform(typeText(content)).check(matches(withText(content)));
        onView(allOf(instanceOf(ImageButton.class), withParent(withId(R.id.create_note_toolbar)))).perform(click());
        intended(hasComponent(ListNotes.class.getCanonicalName()));
        Intents.release();
    }
}