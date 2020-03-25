package com.example.concordia_campus_guide.Models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class CalendarEventTest {
    CalendarEvent calendarEvent;
    String title = "Lecture: SOEN 357";
    String location = "H-863";
    String startTime = "1585098000000";

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        calendarEvent = new CalendarEvent(title, location, startTime);
    }

    @Test
    public void getTitle() {
        assertEquals("Lecture: SOEN 357", calendarEvent.getTitle());
    }

    @Test
    public void setTitle() {
        calendarEvent.setTitle("Tutorial: COMP 249");
        assertEquals("Tutorial: COMP 249", calendarEvent.getTitle());
    }

    @Test
    public void getLocation() {
        assertEquals("H-863", calendarEvent.getLocation());
    }

    @Test
    public void setLocation() {
        calendarEvent.setLocation("H-963");
        assertEquals("H-963", calendarEvent.getLocation());
    }

    @Test
    public void getStartTime() {
        assertEquals("1585098000000", calendarEvent.getStartTime());
    }

    @Test
    public void testToString() {
        assertEquals("CalendarEvent{" +
                "title='" + "Lecture: SOEN 357" + '\'' +
                ", location='" + "H-863" + '\'' +
                ", startTime='" + "1585098000000" + '\'' +
                '}', calendarEvent.toString());
    }
}