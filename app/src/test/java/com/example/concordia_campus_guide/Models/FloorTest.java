package com.example.concordia_campus_guide.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FloorTest {
    Floor defaultFloor;
    Floor floorWithArgs;
    Floor floorWithCampus;
    Coordinates coordinates = new Coordinates(45.4972685,-73.5789475);
    String floorCode = "H-9";
    float altitude = 0;
    String campus = "SGW";

    @Before
    public void init(){
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
        assertEquals("H", floorWithArgs.getBuildingCode());
    }

    @Test
    public void getFloorCode() {
        assertNull(defaultFloor.getFloorCode());
        assertEquals("H-9", floorWithArgs.getFloorCode());
        assertEquals("H-9", floorWithCampus.getFloorCode());
    }

    @Test
    public void setFloorCode() {
        assertEquals("H-9", floorWithArgs.getFloorCode());
        floorWithArgs.setFloorCode("H-8");
        assertEquals("H-8", floorWithArgs.getFloorCode());
    }

    @Test
    public void getAltitude() {
        assertEquals(0, defaultFloor.getAltitude(),0.001);
        assertEquals(0, floorWithArgs.getAltitude(),0.001);
        assertEquals(0, floorWithCampus.getAltitude(),0.001);
    }

    @Test
    public void setAltitude() {
        assertEquals(0, floorWithArgs.getAltitude(),0.001);
        floorWithArgs.setAltitude((float) 30.00);
        assertEquals(30.00, floorWithArgs.getAltitude(), 0.001);
    }

    @Test
    public void getDisplayName() {
        assertNull(defaultFloor.getDisplayName());
        assertEquals("H-9", floorWithArgs.getDisplayName());
        assertEquals("H-9", floorWithCampus.getDisplayName());
    }
}