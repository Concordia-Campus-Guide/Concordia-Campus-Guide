package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Direction;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DirectionTest {
    private Direction direction;
    private TestUtilsRoutes testUtils;
    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
        direction = testUtils.direction;
    }

    @Test
    public void getAndSetStartTest() {
        LatLng startTemp = direction.getStart();
        LatLng startNew = new LatLng(-73.57921063899994, 45.49707133596979);
        direction.setStart(startNew);
        assertEquals(direction.getStart(), startNew);
        direction.setStart(startTemp);
    }
    @Test
    public void getAndSetEndTest() {
        LatLng endTemp = direction.getEnd();
        LatLng endNew = new LatLng(-73.57907921075821, 45.49702057370776);
        direction.setEnd(endNew);
        assertEquals(direction.getEnd(), endNew);
        direction.setStart(endTemp);
    }
    @Test
    public void getAndSetTransitTypeTest() {
        String transportTypeOg = direction.getTransportType();
        String transportTypeNew = ClassConstants.TRANSIT;
        direction.setTransportType(transportTypeNew);
        assertEquals(direction.getTransportType(), transportTypeNew);
        direction.setTransportType(transportTypeOg);
    }
    @Test
    public void getAndSetDescriptionTest() {
        String descriptionOg = direction.getDescription();
        String descriptionNew = "This is not a test";
        direction.setDescription(descriptionNew);
        assertEquals(direction.getDescription(), descriptionNew);
        direction.setDescription(descriptionOg);
    }

    @Test
    public void getAndSetDuration() {
        double expectedDuration = 10;
        direction.setDuration(expectedDuration);
        assertEquals(expectedDuration, direction.getDuration());
        direction.setDuration(20);
    }
}
