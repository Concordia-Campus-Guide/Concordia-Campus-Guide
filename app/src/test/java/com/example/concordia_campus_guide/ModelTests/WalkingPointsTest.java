package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.Models.WalkingPoints;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class WalkingPointsTest {

    WalkingPoints walkingPoints;
    List<WalkingPoint> walkingPointsList;
    @Before
    public void init() {
        walkingPointsList = new ArrayList<>();
        walkingPointsList.add(new WalkingPoint(new Coordinates(-70.04, 45.45), "H-8",new ArrayList<>(Arrays.asList(new Integer[]{2,3})), PointType.CLASSROOM, "834"));
        walkingPointsList.add(new WalkingPoint(new Coordinates(-73.57907921075821, 45.49702057370776), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{1,3})), PointType.CLASSROOM,"937"));

        walkingPoints = new WalkingPoints(walkingPointsList);
    }

    @Test
    public void getWalkingPointsTest() {
        assertEquals(walkingPoints.getWalkingPoints(), walkingPointsList);
        assertEquals(new WalkingPoints().getWalkingPoints(), new ArrayList<WalkingPoint>());
    }
    @Test
    public void setWalkingPointsTest() {
        List<WalkingPoint> walkingPointsListTemp = new ArrayList<>();
        walkingPointsListTemp.add(new WalkingPoint(new Coordinates(-55, 45), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{2,3,5})), PointType.ELEVATOR, "elevators"));
        List<WalkingPoint> temp = walkingPoints.getWalkingPoints();
        walkingPoints.setWalkingPoints(walkingPointsListTemp);
        assertEquals(walkingPoints.getWalkingPoints(), walkingPointsListTemp);
        walkingPoints.setWalkingPoints(temp);
    }
}
