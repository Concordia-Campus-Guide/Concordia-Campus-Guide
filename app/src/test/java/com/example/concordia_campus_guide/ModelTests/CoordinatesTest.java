package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.Models.Coordinates;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CoordinatesTest {
    Coordinates coordinates;

    @Before
    public void init() {
        coordinates = new Coordinates(-73.57907921075821, 45.49702057370776);
    }

    @Test
    public void getSetLatitude() {
        double expected = -73.57907921075821;
        coordinates.setLatitude(expected);
        assertEquals(expected, coordinates.getLatitude());
    }

    @Test
    public void getSetLongitude() {
        double expected = 45.49702057370776;
        coordinates.setLongitude(expected);
        assertEquals(expected, coordinates.getLongitude());
    }

}
