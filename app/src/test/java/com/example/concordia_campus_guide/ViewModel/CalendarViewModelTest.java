package com.example.concordia_campus_guide.ViewModel;

import android.database.Cursor;

import com.example.concordia_campus_guide.Fragments.CalendarFragment.CalendarViewModel;
import com.example.concordia_campus_guide.Models.CalendarEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
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
    public void getCalendarEvents() {
        ArrayList<CalendarEvent> list = new ArrayList<>(mViewModel.getCalendarEvents(cursor));
        assertFalse(list.isEmpty());
    }
}