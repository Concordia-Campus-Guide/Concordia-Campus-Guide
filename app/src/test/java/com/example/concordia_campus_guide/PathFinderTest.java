package com.example.concordia_campus_guide;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PathFinderTest {

    private PathFinder testPathFinder;
    private Context context;
    private AppDatabase database;

    @Before
    public void init() {
        context = mock(Context.class);
        database = mock(AppDatabase.class);

        mockAppDataBase();

        RoomModel src = new RoomModel(new Double[]{-73.57888643, 45.4974255}, "H-8", "N/A");
        RoomModel destination = new RoomModel(new Double[]{-73.57858669, 45.49724054}, "H-9", "N/A");
        testPathFinder = new PathFinder(context, src, destination);

        List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
    }

    public void mockAppDataBase() {
        when(AppDatabase.getInstance(context)).thenReturn(database);
        when(database.walkingPointDao().getAll()).thenReturn(WalkingPointTestUtil.walkingPointList);
    }

    @Test
    public void populateWalkingPointMapTest() {
        HashMap<Integer, WalkingPointNode> map = new HashMap<>();
        map = testPathFinder.populateWalkingPointMap(WalkingPointTestUtil.walkingPointList);
    }

    @Test
    public void getWalkingPointCorrespondingToRoom() {
    }

    @Test
    public void getPathToDestination() {
    }

    @Test
    public void isGoal() {
    }

    @Test
    public void getSolutionPath() {
    }

    @Test
    public void addNearestWalkingPoints() {
    }

    @Test
    public void updateWalkingNode() {
    }

    @Test
    public void getEuclideanDistance() {
    }

    @Test
    public void computeEstimatedCostFromInitialToDestination() {
    }

    @Test
    public void computeHeuristicEstimate() {
    }

    @Test
    public void getNearestAccessPointForFloor() {
    }

    @Test
    public void getClosestPointToCurrentPointFromList() {
    }

    @Test
    public void getWalkingPointNodesMap() {
    }

    @Test
    public void getWalkingPointsToVisit() {
    }

    @Test
    public void getWalkingPointsVisited() {
    }

    @Test
    public void getInitialPoint() {
    }

    @Test
    public void getDestinationPoint() {
    }
}
