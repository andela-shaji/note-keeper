package com.checkpoint.andela.notekeeper.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
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
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdashBoardTest {

    @Rule
    public ActivityTestRule rule1 = new ActivityTestRule(DashBoard.class);

    @Test
    public void testCreateNoteOnClick() throws Exception {
        Intents.init();
        onView(withId(R.id.dashboard_create_note)).perform(click());
        intended(hasComponent(CreateNote.class.getCanonicalName()));
        Intents.release();
    }

    @Test
    public void testSettingsOnClick() throws Exception {
        Intents.init();
        onView(withId(R.id.dashboard_settings_note)).perform(click());
        intended(hasComponent(AppSettings.class.getCanonicalName()));
        Intents.release();
    }

    @Test
    public void testListNoteOnClick() throws Exception {
        Intents.init();
        onView(withId(R.id.dashboard_list_notes)).perform(click());
        intended(hasComponent(ListNotes.class.getCanonicalName()));
        Intents.release();
    }
}