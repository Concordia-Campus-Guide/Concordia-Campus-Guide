package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.Models.Place;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FloorsTest {
    TestUtils testUtils;

    @Before
    public void init(){
        testUtils = new TestUtils();
    }

    @Test
    public void getFloors() {
        assertTrue(testUtils.floors.getFloors().isEmpty());
    }

    @Test
    public void setFloors(){
        assertTrue(testUtils.floors.getFloors().isEmpty());
        testUtils.floors.setFloors(testUtils.listOfFloors);
        assertFalse(testUtils.floors.getFloors().isEmpty());
    }

    @Test
    public void getPlaces() {
        testUtils.floors.setFloors(testUtils.listOfFloors);
        List<Place> places = new ArrayList<Place>();
        places = testUtils.floors.getPlaces();
        assertEquals(2, places.size());
    }
}