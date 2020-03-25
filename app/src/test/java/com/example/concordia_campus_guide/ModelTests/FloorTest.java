package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class FloorTest {
    Floor defaultFloor;
    Floor floorWithArgs;
    Floor floorWithCampus;
    Coordinates coordinates = new Coordinates(45.4972685, -73.5789475);
    String floorCode = "H-9";
    float altitude = 0;
    String campus = "SGW";

    @Before
    public void init() {
        defaultFloor = new Floor();
        floorWithArgs = new Floor(coordinates, floorCode, altitude);
        floorWithCampus = new Floor(coordinates, floorCode, altitude, campus);
    }

    @Test
    public void getBuildingCode() {
        assertNull(defaultFloor.getBuildingCode());
    }

    @Test
    public void setBuildingCode() {
        assertNull(floorWithArgs.getBuildingCode());
        floorWithArgs.setBuildingCode("H");
        Assert.assertEquals("H", floorWithArgs.getBuildingCode());
    }

    @Test
    public void getFloorCode() {
        assertNull(defaultFloor.getFloorCode());
        Assert.assertEquals("H-9", floorWithArgs.getFloorCode());
        Assert.assertEquals("H-9", floorWithCampus.getFloorCode());
    }

    @Test
    public void setFloorCode() {
        Assert.assertEquals("H-9", floorWithArgs.getFloorCode());
        floorWithArgs.setFloorCode("H-8");
        Assert.assertEquals("H-8", floorWithArgs.getFloorCode());
    }

    @Test
    public void getAltitude() {
        Assert.assertEquals(0, defaultFloor.getAltitude(), 0.001);
        Assert.assertEquals(0, floorWithArgs.getAltitude(), 0.001);
        Assert.assertEquals(0, floorWithCampus.getAltitude(), 0.001);
    }

    @Test
    public void setAltitude() {
        Assert.assertEquals(0, floorWithArgs.getAltitude(), 0.001);
        floorWithArgs.setAltitude((float) 30.00);
        Assert.assertEquals(30.00, floorWithArgs.getAltitude(), 0.001);
    }

    @Test
    public void getDisplayName() {
        assertNull(defaultFloor.getDisplayName());
        Assert.assertEquals("H-9", floorWithArgs.getDisplayName());
        Assert.assertEquals("H-9", floorWithCampus.getDisplayName());
    }
}
