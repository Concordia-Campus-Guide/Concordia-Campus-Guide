package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Routes.Bus;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DirectionTest {
    private Direction direction;
    private TestUtils testUtils;
    @Before
    public void init() {
        testUtils = new TestUtils();
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
        TransportType transportTypeOg = direction.getType();
        TransportType transportTypeNew = new Bus(testUtils.getDirectionStepsObject());
        direction.setType(transportTypeNew);
        assertEquals(direction.getType(), transportTypeNew);
        direction.setType(transportTypeOg);
    }
    @Test
    public void getAndSetDescriptionTest() {
        String descriptionOg = direction.getDescription();
        String descriptionNew = "This is not a test";
        direction.setDescription(descriptionNew);
        assertEquals(direction.getDescription(), descriptionNew);
        direction.setDescription(descriptionOg);
    }
}
