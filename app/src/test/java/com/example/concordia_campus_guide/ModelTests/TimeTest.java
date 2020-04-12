package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.models.Time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {
    Time time;

    @Before
    public void init() {
        time = new Time();
    }

    @Test
    public void timeConstructor(){
        assertNotNull(time);
    }

    @Test
    public void getSetTextTest(){
        assertNull(time.getText());
        time.setText("4:23pm");
        assertEquals("4:23pm", time.getText());
        time.setText(null);
    }

    @Test
    public void getSetValueTest(){
        assertEquals((long)0, time.getValue());
        time.setValue(4);
        assertEquals(4, time.getValue());
        time.setValue(0);
    }

    @Test
    public void getSetTimeZoneTest(){
        assertNull(time.getTimeZone());
        time.setTimeZone("PT");
        assertEquals("PT", time.getTimeZone());
        time.setTimeZone(null);
    }

}