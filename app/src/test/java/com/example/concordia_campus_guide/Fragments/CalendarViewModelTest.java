package com.example.concordia_campus_guide.Fragments;

import android.database.Cursor;

import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalendarViewModelTest {

    @Mock
    private Cursor cursor;

    private CalendarViewModel mViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mViewModel = new CalendarViewModel();

        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(cursor.getString(anyInt())).thenReturn("test");
    }

    @Test
    public void getCalendarEventNoMatch() {
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchLecture(){
        String containsLecture = "Lecture: SOEN 390";
        when(cursor.getString(anyInt())).thenReturn(containsLecture);
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchTutorial(){
        String containsTutorial = "Tutorial: SOEN 390";
        when(cursor.getString(anyInt())).thenReturn(containsTutorial);
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }

    @Test
    public void getCalendarEventMatchLab(){
        String containsLab = "Lab: COMP 445";
        when(cursor.getString(anyInt())).thenReturn(containsLab);
        CalendarEvent calendarEvent = mViewModel.getCalendarEvent(cursor);
        assertNotNull(calendarEvent);
    }


}