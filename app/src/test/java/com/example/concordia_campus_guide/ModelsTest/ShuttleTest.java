package com.example.concordia_campus_guide.ModelsTest;

import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Models.Shuttles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ShuttleTest {
    Shuttle shuttle;
    Shuttles shuttles;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        shuttle = new Shuttle();
        shuttles = new Shuttles();
    }

    @Test
    public void testShuttleCampus() {
        String expected = "SGW";
        shuttle.setCampus(expected);
        String actual = shuttle.getCampus();
        assertEquals(expected, actual);
    }

    @Test
    public void testShuttleDay() {
        List<String> expected = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday");
        shuttle.setDay(expected);
        List<String> actual = shuttle.getDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testShuttleTime() {
        String expected = "7:05";
        shuttle.setTime(expected);
        String actual = shuttle.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void testShuttles() {
        ArrayList<Shuttle> expected = new ArrayList<Shuttle>();
        expected.add(shuttle);
        shuttles.setShuttles(expected);
        List<Shuttle> actual = shuttles.getShuttles();
        assertEquals(expected, actual);
    }
}
