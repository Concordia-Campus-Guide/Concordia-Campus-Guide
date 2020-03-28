package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class FloorTest {
    TestUtils testUtils = new TestUtils();

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getBuildingCode() {
        assertNull(testUtils.defaultFloor.getBuildingCode());
    }

    @Test
    public void setBuildingCode() {
        assertNull(testUtils.floorWithArgs.getBuildingCode());
        testUtils.floorWithArgs.setBuildingCode(testUtils.buildingCode);
        Assert.assertEquals(testUtils.buildingCode, testUtils.floorWithArgs.getBuildingCode());
    }

    @Test
    public void getFloorCode() {
        assertNull(testUtils.defaultFloor.getFloorCode());
        Assert.assertEquals(testUtils.floorCode, testUtils.floorWithArgs.getFloorCode());
        Assert.assertEquals(testUtils.floorCode, testUtils.floorWithCampus.getFloorCode());
    }

    @Test
    public void setFloorCode() {
        Assert.assertEquals(testUtils.floorCode, testUtils.floorWithArgs.getFloorCode());
        testUtils.floorWithArgs.setFloorCode("H-8");
        Assert.assertEquals("H-8", testUtils.floorWithArgs.getFloorCode());
    }

    @Test
    public void getAltitude() {
        Assert.assertEquals(0, testUtils.defaultFloor.getAltitude(), 0.001);
        Assert.assertEquals(0, testUtils.floorWithArgs.getAltitude(), 0.001);
        Assert.assertEquals(0, testUtils.floorWithCampus.getAltitude(), 0.001);
    }

    @Test
    public void setAltitude() {
        Assert.assertEquals(0, testUtils.floorWithArgs.getAltitude(), 0.001);
        testUtils.floorWithArgs.setAltitude((float) 30.00);
        Assert.assertEquals(30.00, testUtils.floorWithArgs.getAltitude(), 0.001);
    }

    @Test
    public void getDisplayName() {
        assertNull(testUtils.defaultFloor.getDisplayName());
        Assert.assertEquals(testUtils.floorCode, testUtils.floorWithArgs.getDisplayName());
        Assert.assertEquals(testUtils.floorCode, testUtils.floorWithCampus.getDisplayName());
    }
}
