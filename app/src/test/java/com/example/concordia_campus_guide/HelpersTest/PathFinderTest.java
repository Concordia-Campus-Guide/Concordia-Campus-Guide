package com.example.concordia_campus_guide.HelpersTest;

import android.content.SharedPreferences;
import android.graphics.Color;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Building;
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
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class PathFinderTest {
    private WalkingPoint walkingPoint1;
    private WalkingPoint walkingPoint2;
    private WalkingPoint walkingPoint3;
    private WalkingPoint walkingPoint4;
    private List<WalkingPoint> walkingPointList;
    private PathFinder pathFinder;
    private RoomModel room1;
    private RoomModel room2;

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

        room1 = new RoomModel(new Coordinates(-73.57876237, 45.49729154), "823", "H-8");
        room2 = new RoomModel(new Coordinates(-73.57883681, 445.49731974), "813", "H-8");
        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);
        walkingPointList = new ArrayList<>();
        walkingPointList.add(walkingPoint1);
        walkingPointList.add(walkingPoint2);
        walkingPointList.add(walkingPoint3);
        walkingPointList.add(walkingPoint4);
        when(mockWalkingPointDao.getAll()).thenReturn(walkingPointList);
        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlaceCode(room1.getRoomCode())).thenReturn(walkingPointList);
        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlaceCode(room2.getRoomCode())).thenReturn(walkingPointList);

        pathFinder = new PathFinder(mockAppDb,room1,room2);
    }



    @Test
    public void isGoalTest(){
        assertFalse(pathFinder.isGoal(walkingPoint2));
    }

    @Test
    public void getWalkingPointCorrespondingToPlaceTest(){
        Building buidling = new Building( new Coordinates(45.4972685, -73.5789475), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null);
        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlaceCode(buidling.getBuildingCode())).thenReturn(walkingPointList);
        assertEquals(pathFinder.getWalkingPointCorrespondingToPlace(buidling),walkingPoint1);
    }

    @Test
    public void getPathToDestinationTest(){
        assertEquals(walkingPointList.get(0),pathFinder.getPathToDestination().get(0));
    }

    @Test
    public void addNearestWalkingPointsTest(){

    }
}
