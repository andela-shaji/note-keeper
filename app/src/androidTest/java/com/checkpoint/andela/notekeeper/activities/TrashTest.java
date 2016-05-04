package com.checkpoint.andela.notekeeper.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ImageButton;

import com.checkpoint.andela.notekeeper.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TrashTest {

    @Rule
    public ActivityTestRule rule1 = new ActivityTestRule(Trash.class);
    @Test
    public void testDisplayNote() {
        onView(withText("Suada")).check((doesNotExist()));
        onView(withText("Semiu")).check(matches(isDisplayed()));
    }


    @Test
    public void testDeleteNote() {
        onView(withText("Kenya")).perform(click());
        onView(withId(R.id.trash_popup)).perform(click());
        onView(withText("Kenya")).check((doesNotExist()));
    }



}