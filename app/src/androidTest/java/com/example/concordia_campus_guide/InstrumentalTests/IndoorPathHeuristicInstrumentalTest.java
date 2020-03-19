package com.example.concordia_campus_guide.InstrumentalTests;


import android.content.Context;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IndoorPathHeuristicInstrumentalTest {
    private Context appContext;
    private IndoorPathHeuristic indoorPathHeuristic;
    private WalkingPoint walkingPointBegin;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void init() {
        walkingPointBegin = new WalkingPoint(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", null, PointType.CLASSROOM);
        appContext = mActivityRule.getActivity().getApplicationContext();
        indoorPathHeuristic = new IndoorPathHeuristic(appContext);
    }

    @Test
    public void getEuclideanDistanceTest(){
        WalkingPoint walkingPointFinish= new WalkingPoint(new Coordinates(-73.57913553714752,45.497045014802595),"H-8", null, PointType.ELEVATOR);
        double expectedValue = 6.140056392116663E-5;
        assertEquals(expectedValue, indoorPathHeuristic.getEuclideanDistance(walkingPointBegin,walkingPointFinish));
    }

    @Test
    public void getClosestPointToCurrentPointFromListTest(){
        List<WalkingPoint> listOfClosestPoints = new ArrayList<>();
        listOfClosestPoints.add(new WalkingPoint(new Coordinates(-73.57913553714752,45.497045014802595),"H-8", null, PointType.ELEVATOR));
        listOfClosestPoints.add(new WalkingPoint(new Coordinates(-73.57933938503265, 45.49712773842951), "H-8",null, PointType.CLASSROOM));
        listOfClosestPoints.add(new WalkingPoint(new Coordinates(-73.57933938503265, 45.497268744331606), "H-8", null, PointType.CLASSROOM));

        assertEquals(listOfClosestPoints.get(0), indoorPathHeuristic.getClosestPointToCurrentPointFromList(walkingPointBegin,listOfClosestPoints));
    }

    @Test
    public void getNearestAccessPointForFloorTest() {
        WalkingPoint walkingPointFound = indoorPathHeuristic.getNearestAccessPointForFloor(walkingPointBegin);
        WalkingPoint expectedWalkingPoint = new WalkingPoint(new Coordinates(-73.57876237,45.49729154),"H-8",null, PointType.ELEVATOR);
        assertEquals(expectedWalkingPoint,walkingPointFound);
    }

    @Test
    public void computeHeuristicTest(){
        WalkingPoint walkingPointFinish= new WalkingPoint(new Coordinates(-73.57913553714752,45.497045014802595),"H-8", null, PointType.ELEVATOR);
        double expectedHeuristicValue = 6.140056392116663E-5;
        assertEquals(expectedHeuristicValue,indoorPathHeuristic.computeHeuristic(walkingPointBegin,walkingPointFinish));
        walkingPointFinish.setFloorCode("H-9");
        expectedHeuristicValue  =  8.641515602217892E-4;
        assertEquals(expectedHeuristicValue, indoorPathHeuristic.computeHeuristic(walkingPointBegin,walkingPointFinish));

    }
}
