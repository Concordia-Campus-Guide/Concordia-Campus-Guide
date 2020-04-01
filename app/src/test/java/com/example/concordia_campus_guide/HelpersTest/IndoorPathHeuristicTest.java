package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndoorPathHeuristicTest {
    private IndoorPathHeuristic indoorPathHeuristic;
    private WalkingPoint walkingPoint1;
    private WalkingPoint walkingPoint2;
    private WalkingPoint walkingPoint3;
    private WalkingPoint walkingPoint4;
    private WalkingPoint walkingPoint5;
    private WalkingPoint walkingPoint6;
    private WalkingPoint walkingPoint7;
    private WalkingPoint walkingPoint8;
    private List<WalkingPoint> walkingPointList;


    @Mock
    AppDatabase mockAppDb;

    @Mock
    WalkingPointDao mockWalkingPointDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        walkingPoint1 = new WalkingPoint(1,new Coordinates(1, 1), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{2,3,4})), PointType.CLASSROOM, "937");
        walkingPoint2 = new WalkingPoint(2,new Coordinates(2,2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3})),PointType.NONE,"NONE");
        walkingPoint3 = new WalkingPoint(3,new Coordinates(1,2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2,4})),PointType.NONE,"NONE");
        walkingPoint4 = new WalkingPoint(4,new Coordinates(-1, -2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3,5,6})),PointType.CLASSROOM,"921");
        walkingPoint5 = new WalkingPoint(5,new Coordinates(2, 3), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{4,8})),PointType.ENTRANCE,"entrance");
        walkingPoint6 = new WalkingPoint(6,new Coordinates(2, 1), "H-8", new ArrayList<>(Arrays.asList(new Integer[]{4,7})),PointType.ELEVATOR,"elevator");
        walkingPoint7 = new WalkingPoint(7,new Coordinates(4, -1), "H-8", new ArrayList<>(Arrays.asList(new Integer[]{6})),PointType.CLASSROOM,"832");
        walkingPoint8 = new WalkingPoint(8,new Coordinates(3, 1), "MB-2", new ArrayList<>(Arrays.asList(new Integer[]{5})),PointType.CLASSROOM,"S-23");

        walkingPointList = Arrays.asList(walkingPoint1, walkingPoint2, walkingPoint3, walkingPoint4, walkingPoint5, walkingPoint6, walkingPoint6, walkingPoint8);

        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(walkingPoint1.getFloorCode(),PointType.ELEVATOR)).thenReturn(walkingPointList);
        when(mockWalkingPointDao.getAll()).thenReturn(walkingPointList);
        indoorPathHeuristic = new IndoorPathHeuristic(mockAppDb);
    }

    @Test
    public void getEuclideanDistanceTest(){
        double expectedValue = 1.4142135623730951;
        assertEquals(expectedValue, indoorPathHeuristic.getEuclideanDistance(walkingPoint1,walkingPoint2));
    }

    @Test
    public void getClosestPointToCurrentPointFromListTest(){
        List<WalkingPoint> closestPoint = new ArrayList<>();
        closestPoint.add(walkingPoint2);
        closestPoint.add(walkingPoint4);
        assertEquals(walkingPoint2, indoorPathHeuristic.getClosestPointToCurrentPointFromList(walkingPoint1,closestPoint));
    }

    @Test
    public void getNearestAccessPointForFloorTest() {
        List<WalkingPoint> temp = new ArrayList<>();
        temp.add(walkingPoint4);
        temp.add(walkingPoint3);
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(walkingPoint1.getFloorCode(),PointType.CLASSROOM)).thenReturn(temp);

        WalkingPoint walkingPointFound = indoorPathHeuristic.getNearestAccessPointForFloor(walkingPoint1,PointType.CLASSROOM);
        assertEquals(walkingPoint3,walkingPointFound);
    }


    @Test
    public void computeHeuristicSameFloorTest(){
        double expectedHeuristicValue = 1.4142135623730951;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(walkingPoint1,walkingPoint2));
    }

    @Test
    public void computeHeuristicDifferentFloorTest(){
        double expectedHeuristicValue = 3.605551275463989;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(walkingPoint1,walkingPoint7));
    }

    @Test
    public void computeHeuristicDifferentBuildingsTest(){
        double expectedHeuristicValue = 2.0;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(walkingPoint1,walkingPoint8));
    }
}
