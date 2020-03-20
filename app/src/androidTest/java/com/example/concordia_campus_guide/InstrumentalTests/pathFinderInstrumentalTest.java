package com.example.concordia_campus_guide.InstrumentalTests;

import android.content.Context;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class pathFinderInstrumentalTest {
    private Context appContext;
    private PathFinder pathFinder;
    private RoomModel roomSource;
    private RoomModel roomDestination;
    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void init() {
        appContext = mActivityRule.getActivity().getApplicationContext();
        roomSource = new RoomModel(new Double[]{-73.57907921075821, 45.49702057370776}, "833", "H-8");
        roomDestination  = new RoomModel(new Double[]{-73.57902321964502, 45.49699848270905}, "835", "H-8");
        pathFinder = new PathFinder(appContext, roomSource, roomDestination);
    }

    @Test
    public void getWalkingPointCorrespondingToRoomTest(){
        List<WalkingPoint> walkingPoints = new ArrayList<>();
        WalkingPoint walkingPointTest = new WalkingPoint(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", null, PointType.CLASSROOM);
        walkingPoints.add(walkingPointTest);
        assertEquals(pathFinder.getWalkingPointCorrespondingToRoom(roomSource, walkingPoints), walkingPointTest);
    }

    @Test
    public void isGoalTest(){
        assertTrue(pathFinder.isGoal(new WalkingPoint(new Coordinates(-73.57902321964502, 45.49699848270905), "H-8", null, PointType.CLASSROOM)));
    }
}
