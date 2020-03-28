package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class WalkingPointTest {

    WalkingPoint walkingPoint;

    @Before
    public void init() {
        walkingPoint = new WalkingPoint(new Coordinates(-70.04, 45.45), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{1,2,3})), PointType.CLASSROOM, "937");
    }

    @Test
    public void hashCodeTest() {
        int expected = Objects.hash(walkingPoint.getCoordinate(), walkingPoint.getFloorCode());
        assertEquals(expected, walkingPoint.hashCode());
    }
    @Test
    public void getAndSetIdTest() {
        int id = walkingPoint.getId();
        walkingPoint.setId(800);
        assertEquals(walkingPoint.getId(), 800);
        walkingPoint.setId(id);
    }
    @Test
    public void getAndSetCoordinateTest() {
        Coordinates coordinate = walkingPoint.getCoordinate();
        walkingPoint.setCoordinate(new Coordinates(-66.5, 45.45));
        assertEquals(walkingPoint.getCoordinate(), new Coordinates(-66.5, 45.45));
        walkingPoint.setCoordinate(coordinate);
    }
    @Test
    public void getAndSetFloorCodeTest() {
        String floorCode = walkingPoint.getFloorCode();
        walkingPoint.setFloorCode("H-8");
        assertEquals(walkingPoint.getFloorCode(), "H-8");
        walkingPoint.setFloorCode(floorCode);
    }
    @Test
    public void getAndSetConnectedPointIdTest() {
        List<Integer> newConnectedPoint = new ArrayList<>(Arrays.asList(new Integer[]{3,4}));
        List<Integer> connectedPointId = walkingPoint.getConnectedPointsId();
        walkingPoint.setConnectedPointsId(newConnectedPoint);
        assertEquals(walkingPoint.getConnectedPointsId(), newConnectedPoint);
        walkingPoint.setConnectedPointsId(connectedPointId);
    }
    @Test
    public void getAndSetPointTypeTest() {
        PointType pointType = walkingPoint.getPointType();
        walkingPoint.setPointType(PointType.ELEVATOR);
        assertEquals(walkingPoint.getPointType(), PointType.ELEVATOR);
        walkingPoint.setPointType(pointType);
    }

    @Test
    public void equalsTest(){
        WalkingPoint walkingPointTwo = new WalkingPoint(new Coordinates(-70.04, 45.45), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{})), PointType.ELEVATOR, "elevators");
        assertTrue(walkingPoint.equals(walkingPointTwo));
        assertTrue(walkingPoint.equals(walkingPoint));
        assertFalse(walkingPoint.equals(null));
        assertFalse(walkingPoint.equals("this is a string"));
    }

    @Test
    public void getSetPlaceCodeTest() {
        walkingPoint.setPlaceCode("920");
        assertEquals("920", walkingPoint.getPlaceCode());
        walkingPoint.setPlaceCode("937");
    }

    @Test
    public void toStringTest() {
        String expected = "WalkingPoint{id=" + walkingPoint.getId() +
                ", coordinate=" + walkingPoint.getCoordinate() +
                ", floorCode='H-9', connectedPointsId=" + walkingPoint.getConnectedPointsId() +
                ", pointType=" + walkingPoint.getPointType() + '}';
        assertEquals(expected, walkingPoint.toString());
    }

}