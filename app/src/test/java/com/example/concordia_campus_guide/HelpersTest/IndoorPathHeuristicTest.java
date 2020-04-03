package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
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
    private WalkingPoint classRoomInHBuildingWalkingPoint1;
    private WalkingPoint walkingPoint2;
    private WalkingPoint walkingPoint3;
    private WalkingPoint walkingPoint4;
    private WalkingPoint classRoomInHBuildingWalkingPoint2;
    private WalkingPoint entranceHBuildingWalkingPoint;
    private WalkingPoint elevatorWalkingPointH9;
    private WalkingPoint entranceMBBuildingWalkingPoint;
    private WalkingPoint walkingPoint9;
    private WalkingPoint elevatorWalkingPointH8;
    private WalkingPoint classRoomInMBBuildingWalkingPoint;
    private List<WalkingPoint> walkingPointList;
    private WalkingPoint walkingPoint12;
    private IndoorPathHeuristic indoorPathHeuristic;
    private WalkingPoint classRoomInHBuildingWalkingPointh8;

    @Mock
    AppDatabase mockAppDb;

    @Mock
    WalkingPointDao mockWalkingPointDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        settingUpWalkingPoints();

        walkingPointList = Arrays.asList(classRoomInHBuildingWalkingPoint1, walkingPoint2, walkingPoint3, walkingPoint4, classRoomInHBuildingWalkingPoint2,
                entranceHBuildingWalkingPoint, elevatorWalkingPointH9,entranceMBBuildingWalkingPoint, walkingPoint9, classRoomInMBBuildingWalkingPoint);

        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(classRoomInHBuildingWalkingPoint1.getFloorCode(),PointType.ENTRANCE)).thenReturn(Arrays.asList(classRoomInHBuildingWalkingPoint1));
        when(mockWalkingPointDao.getAll()).thenReturn(walkingPointList);
        indoorPathHeuristic = new IndoorPathHeuristic(mockAppDb);
    }

    private void settingUpWalkingPoints(){
        classRoomInHBuildingWalkingPoint1 = new WalkingPoint(1,new Coordinates(1, 1), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{2,3,4})), PointType.CLASSROOM, "937");
        walkingPoint2 = new WalkingPoint(2,new Coordinates(2,2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3,7})),PointType.NONE,"NONE");
        walkingPoint3 = new WalkingPoint(3,new Coordinates(2,-2.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2,4,5})),PointType.NONE,"NONE");
        walkingPoint4 = new WalkingPoint(4,new Coordinates(1, 1.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3,5,6})),PointType.NONE,"NONE");
        classRoomInHBuildingWalkingPoint2 = new WalkingPoint(5,new Coordinates(5, 5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{3,4})),PointType.CLASSROOM,"921");
        entranceHBuildingWalkingPoint = new WalkingPoint(6,new Coordinates(1.2, 2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{4,8})),PointType.ENTRANCE,"ENTRANCE");
        elevatorWalkingPointH9 = new WalkingPoint(7,new Coordinates(1, 3), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2,11})),PointType.ELEVATOR,"elevators-1");
        elevatorWalkingPointH8 = new WalkingPoint(11,new Coordinates(1, 3), "H-8", new ArrayList<>(Arrays.asList(new Integer[]{7,12})),PointType.ELEVATOR,"elevators-1");
        entranceMBBuildingWalkingPoint = new WalkingPoint(8, new Coordinates(2.4,3.2),"MB-S2",new ArrayList<>(Arrays.asList(new Integer[]{7,9})),PointType.ENTRANCE, "ENTRANCE");
        walkingPoint9 = new WalkingPoint(9,new Coordinates(-2.4,2.2),"MB-S2",new ArrayList<>(Arrays.asList(new Integer[]{8,10})),PointType.NONE, "NONE");
        classRoomInMBBuildingWalkingPoint = new WalkingPoint(10,new Coordinates(2.45,-2.2),"MB-S2",new ArrayList<>(Arrays.asList(new Integer[]{9})),PointType.CLASSROOM, "279");
        walkingPoint12 = new WalkingPoint(12,new Coordinates(2, 3), "H-8", new ArrayList<>(Arrays.asList(new Integer[]{11,13})),PointType.NONE,"NONE");
        classRoomInHBuildingWalkingPointh8 =new WalkingPoint(13,new Coordinates(1, 1), "H-8",new ArrayList<>(Arrays.asList(new Integer[]{12})), PointType.CLASSROOM, "837");
    }

    @Test
    public void getEuclideanDistanceTest(){
        double expectedValue = 1.4142135623730951;
        assertEquals(expectedValue, indoorPathHeuristic.getEuclideanDistance(classRoomInHBuildingWalkingPoint1, walkingPoint2));
    }

    @Test
    public void getClosestPointToCurrentPointFromListTest(){
        List<WalkingPoint> closestPoint = new ArrayList<>();
        closestPoint.add(walkingPoint2);
        closestPoint.add(walkingPoint4);
        assertEquals(walkingPoint4, indoorPathHeuristic.getClosestPointToCurrentPointFromList(classRoomInHBuildingWalkingPoint1,closestPoint));
    }

    @Test
    public void getNearestAccessPointForFloorTest() {
        List<WalkingPoint> temp = new ArrayList<>();
        temp.add(walkingPoint4);
        temp.add(walkingPoint3);
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(classRoomInHBuildingWalkingPoint1.getFloorCode(),PointType.CLASSROOM)).thenReturn(temp);

        WalkingPoint walkingPointFound = indoorPathHeuristic.getNearestAccessPointForFloor(classRoomInHBuildingWalkingPoint1,PointType.CLASSROOM);
        assertEquals(walkingPoint4,walkingPointFound);
    }


    @Test
    public void computeHeuristicSameFloorTest(){
        double expectedHeuristicValue = 1.4142135623730951;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(classRoomInHBuildingWalkingPoint1, walkingPoint2));
    }

    @Test
    public void computeHeuristicDifferentFloorTest(){
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(classRoomInHBuildingWalkingPoint1.getFloorCode(),PointType.ELEVATOR)).thenReturn(Arrays.asList(elevatorWalkingPointH9));
        double expectedHeuristicValue = 4.0;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(classRoomInHBuildingWalkingPoint1,classRoomInHBuildingWalkingPointh8));
    }

    @Test
    public void computeHeuristicDifferentBuildingsTest(){
        double expectedHeuristicValue = 3.513189434118235;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(classRoomInHBuildingWalkingPoint1,classRoomInMBBuildingWalkingPoint));
    }
}
