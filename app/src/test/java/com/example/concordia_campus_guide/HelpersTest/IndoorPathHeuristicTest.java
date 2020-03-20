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
    private List<WalkingPoint> walkingPointList;


    @Mock
    AppDatabase mockAppDb;

    @Mock
    WalkingPointDao mockWalkingPointDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        walkingPoint1 = new WalkingPoint(1,new Coordinates(-70.04, 45.45), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{2,3,4})), PointType.CLASSROOM, "937");
        walkingPoint2 = new WalkingPoint(2,new Coordinates(-73.57876237,45.49729154), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3})),PointType.NONE,"NONE");
        walkingPoint3 = new WalkingPoint(3,new Coordinates(-73.57883681,45.49731974), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2,4})),PointType.NONE,"NONE");
        walkingPoint4 = new WalkingPoint(4,new Coordinates(-73.57866548, 45.49746803), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3})),PointType.CLASSROOM,"921");

        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);

        walkingPointList = new ArrayList<>();
        walkingPointList.add(walkingPoint1);
        walkingPointList.add(walkingPoint2);
        walkingPointList.add(walkingPoint3);
        walkingPointList.add(walkingPoint4);

        when(mockWalkingPointDao.getAll()).thenReturn(walkingPointList);
        indoorPathHeuristic = new IndoorPathHeuristic(mockAppDb);
    }

    @Test
    public void getEuclideanDistanceTest(){
        double expectedValue = 3.5390783547533378;
        assertEquals(expectedValue, indoorPathHeuristic.getEuclideanDistance(walkingPoint1,walkingPoint2));
    }

    @Test
    public void getClosestPointToCurrentPointFromListTest(){
        List<WalkingPoint> closestPoint = new ArrayList<>();
        closestPoint.add(walkingPoint2);
        closestPoint.add(walkingPoint4);
        assertEquals(walkingPoint4, indoorPathHeuristic.getClosestPointToCurrentPointFromList(walkingPoint1,closestPoint));
    }

    @Test
    public void getNearestAccessPointForFloorTest() {
        List<WalkingPoint> temp = new ArrayList<>();
        temp.add(walkingPoint4);
        temp.add(walkingPoint3);
        when(mockWalkingPointDao.getAllAccessPointsOnFloor(walkingPoint1.getFloorCode(),PointType.CLASSROOM)).thenReturn(temp);

        WalkingPoint walkingPointFound = indoorPathHeuristic.getNearestAccessPointForFloor(walkingPoint1,PointType.CLASSROOM);
        assertEquals(walkingPoint4,walkingPointFound);
    }


    @Test
    public void computeHeuristicTest(){
        double expectedHeuristicValue = 3.5390783547533378;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(walkingPoint1,walkingPoint2));
    }
}
