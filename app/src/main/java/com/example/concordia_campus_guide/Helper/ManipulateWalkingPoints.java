package com.example.concordia_campus_guide.Helper;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManipulateWalkingPoints {
    private List<WalkingPoint> walkingPoints;

    public List<WalkingPoint> getWalkingPointsList() {
        return walkingPoints;
    }

    public void parseWalkingPointList(AppDatabase appDatabase, RoomModel from, RoomModel to, HashMap<String, List<WalkingPoint>> walkingPointsMap) {
        PathFinder pf = new PathFinder(appDatabase, from, to);
        walkingPoints = pf.getPathToDestination();

        List<WalkingPoint> floorWalkingPointList;
        for (WalkingPoint wp : walkingPoints) {
            floorWalkingPointList = walkingPointsMap.getOrDefault(wp.getFloorCode(), new ArrayList<WalkingPoint>());
            floorWalkingPointList.add(wp);
            walkingPointsMap.put(wp.getFloorCode(), floorWalkingPointList);
        }
    }
}
