package com.example.concordia_campus_guide.InstrumentalTests;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class CalendarViewModelInstrumentalTest {
    private CalendarViewModel mViewModel;
    private Context appContext;
    private Application application;
    private Cursor cursor;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        application = mActivityRule.getActivity().getApplication();
        mViewModel = new CalendarViewModel(application);
        cursor = mViewModel.getCalendarCursor();
    }

    @Test
    public void incorrectlyFormattedTest() {
        String  incorrectFormat= "H-912";
        String correctFormat = "H-9, 963";
        assertTrue(mViewModel.incorrectlyFormatted(incorrectFormat));
        assertFalse(mViewModel.incorrectlyFormatted(correctFormat));
    }
}
