package com.example.concordia_campus_guide.ModelsTest;

import com.example.concordia_campus_guide.Models.Shuttle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ShuttleTest {
    Shuttle shuttle;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        shuttle = new Shuttle();
    }

    @Test
    public void testGetShuttleCampus() {
        String expected = "SGW";
        shuttle.setCampus(expected);
        String actual = shuttle.getCampus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetShuttleDay() {
        List<String> expected = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday");
        shuttle.setDay(expected);
        List<String> actual = shuttle.getDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetShuttleTime() {
        String expected = "7:05";
        shuttle.setTime(expected);
        String actual = shuttle.getTime();
        assertEquals(expected, actual);
    }
}
