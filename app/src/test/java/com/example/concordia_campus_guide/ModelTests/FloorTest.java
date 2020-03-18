package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Floor;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FloorTest {

    Floor floor;
    @Before
    public void init(){
        floor = new Floor(new Double[]{-73.57907921075821, 45.49702057370776}, "H-8", 45, "SGW");
    }

    @Test
    public void getAndSetFloorCodeTest(){
        assertEquals(floor.getFloorCode(), "H-8");
        floor.setFloorCode("H-9");
        assertEquals(floor.getFloorCode(), "H-9");
        floor.setFloorCode("H-8");
    }

    @Test
    public void getAndSetBuildingCodeTest(){
        floor.setBuildingCode("H");
        assertEquals("H", floor.getBuildingCode());
        floor.setBuildingCode("VL");
        assertEquals("VL", floor.getBuildingCode());
        floor.setBuildingCode("H");
    }

    @Test
    public void getAndSetAltitudeCodeTest(){
        assertEquals(floor.getAltitude(), Float.valueOf(45));
        floor.setAltitude(23);
        assertEquals(floor.getAltitude(), Float.valueOf(23));
        floor.setAltitude(45);
    }

    @Test
    public void getDisplayNameTest(){
        assertEquals(floor.getDisplayName(), "H-8");
        floor.setFloorCode("H-9");
        assertEquals(floor.getDisplayName(), "H-9");
        floor.setFloorCode("H-8");
    }

    @Test
    public void getAndSetCampus(){
        assertEquals(floor.getCampus(), "SGW");
        floor.setCampus("LOY");
        assertEquals(floor.getCampus(), "LOY");
        floor.setCampus("SGW");
    }
}
