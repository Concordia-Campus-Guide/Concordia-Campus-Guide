package com.example.concordia_campus_guide.helper;

import android.content.SharedPreferences;

import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManipulateWalkingPoints {
    private List<WalkingPoint> walkingPoints;

    public List<WalkingPoint> getWalkingPointsList() {
        return walkingPoints;
    }

    public void parseWalkingPointList(AppDatabase appDatabase, SharedPreferences sharedPreferences, RoomModel from, RoomModel to, HashMap<String, List<WalkingPoint>> walkingPointsMap) {
        PathFinder pf = new PathFinder(appDatabase,sharedPreferences, from, to);
        walkingPoints = pf.getPathToDestination();

        List<WalkingPoint> floorWalkingPointList;
        for (WalkingPoint wp : walkingPoints) {
            floorWalkingPointList = walkingPointsMap.getOrDefault(wp.getFloorCode(), new ArrayList<WalkingPoint>());
            floorWalkingPointList.add(wp);
            walkingPointsMap.put(wp.getFloorCode(), floorWalkingPointList);
        }
    }
}
