package com.chandlerobaker.alcchallenge.android.journalapp;

import com.chandlerobaker.alcchallenge.android.journalapp.utilities.Utils;

import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void formatDate_isCorrect() {
        Date date = new Date();

        assertEquals(date.getHours()+":"+date.getMinutes(), Utils.formatDate(date));
    }

    @Test
    public void contractString_isCorrect() {
        assertEquals("Test completed successfully", Utils.contractString("Test completed successfully"));
        assertEquals("This project was about to create an android app which can permit users to write theirs diaries. So I thought it like...", Utils.contractString("This project was about to create an android app which can permit users to write theirs diaries. So I thought it like a numeric intima journal where people can easily write their thoughts (stories, moto, etc.). For that to also make a difference between this app and the other ones I add something called mood. There is up to now 6 different moods in the app (happy, angry, love, sad, tired, sick). So a user has the possibility to specify how he feels when writing his thought or what this new diary entry is related to."));
    }
}