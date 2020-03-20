package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.TransitType;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DirectionTest {
    private Direction direction;

    @Before
    public void init() {
        direction = new Direction(new LatLng(-73.57907921075821, 45.49702057370776), new LatLng(-73.57921063899994, 45.49707133596979), TransitType.BIKE, "This is a description", 20);
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
        LatLng endTemp = direction.getStart();
        LatLng endNew = new LatLng(-73.57907921075821, 45.49702057370776);
        direction.setStart(endNew);
        assertEquals(direction.getStart(), endNew);
        direction.setStart(endTemp);

    }
    @Test
    public void getAndSetTransitTypeTest() {
        TransitType transitTypeOg = direction.getType();
        TransitType transitTypeNew = TransitType.BUS;
        direction.setType(transitTypeNew);
        assertEquals(direction.getType(), transitTypeNew);
        direction.setType(transitTypeOg);

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
