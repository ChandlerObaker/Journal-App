package com.chandlerobaker.alcchallenge.android.journalapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {


    private final String user_note_typed = "Holala mon coeur danse la macaranana là là";

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void success_entry_test() {
        Log.e("@Test", "Performing getting new diary success test");
        //The user types some new note

        Espresso.onView(withId(R.id.edittext_setThoughts))
                .perform(ViewActions.replaceText(user_note_typed));

        //After he clicks on the send buttons
        Espresso.onView(withId(R.id.button_add_thoughts))
                .perform(ViewActions.click());
        //And normally the system should clear the editext zone
        Espresso.onView(withId(R.id.edittext_setThoughts))
                .check(matches(withText("")));


    }

    @Test
    public void fail_entry_test() {
        Log.e("@Test", "Performing getting new diary success test");
        //The user types some new note
        Espresso.onView(withId(R.id.edittext_setThoughts))
                .perform(ViewActions.replaceText(user_note_typed));

        //After he clicks on the send buttons
        Espresso.onView(withId(R.id.button_add_thoughts))
                .perform(ViewActions.click());
        //And normally the system should clear the editext zone
        //But here we will compare the diary entried by the user
        Espresso.onView(withId(R.id.edittext_setThoughts))
                .check(matches(withText(user_note_typed)));
    }
}
