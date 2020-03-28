package com.example.concordia_campus_guide.InstrumentalTests;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import androidx.test.rule.ActivityTestRule;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    public void getCalendarEventNoMatch() {
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchLecture(){
        String containsLecture = "Lecture: SOEN 390";
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchTutorial(){
        String containsTutorial = "Tutorial: SOEN 390";
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchLab(){
        String containsLab = "Lab: COMP 445";
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }
}
