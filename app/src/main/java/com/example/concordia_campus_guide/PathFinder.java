package com.example.concordia_campus_guide;

import android.content.Context;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class PathFinder {

    HashMap<Integer, WalkingPoint> walkingPointMap;
    PriorityQueue<WalkingPointNode> walkingPointsToVisit;
    HashMap<WalkingPoint, Double> walkingPointsVisited;
    WalkingPoint initialCoordinate;
    WalkingPoint destinationGoal;
    WalkingPointNode goalNode;
    Context context;

    public PathFinder(Context context, RoomModel source, RoomModel destination) {

        Comparator<WalkingPointNode> comparator = new WalkingPointComparator();
        this.walkingPointsToVisit = new PriorityQueue<>(comparator);
        this.walkingPointsVisited = new HashMap<>();


        List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
        populateWalkingPointMap(walkingPoints);
        this.context = context;
        this.initialCoordinate = getWalkingPoint(source.getCenterCoordinates(), source.getFloorCode(), walkingPoints);
        this.destinationGoal = getWalkingPoint(destination.getCenterCoordinates(), destination.getFloorCode(), walkingPoints);
    }

    //Helper ctor for testing purposes, don't push with this!!!!
    public PathFinder(Context context, Double[] source, Double[] destination, String floorCode) {
        Comparator<WalkingPointNode> comparator = new WalkingPointComparator();
        this.walkingPointsToVisit = new PriorityQueue<WalkingPointNode>(comparator);
        this.walkingPointMap = new HashMap<>();
        this.walkingPointsVisited = new HashMap<>();

        List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
        populateWalkingPointMap(walkingPoints);

        this.initialCoordinate = getWalkingPoint(source, floorCode, walkingPoints);
        this.destinationGoal = getWalkingPoint(destination, floorCode, walkingPoints);
        searchPathToDestination();
    }

    public WalkingPoint getWalkingPoint(Double[] coordinates, String floorCode, List<WalkingPoint> walkingPointList) {
        //TODO: If no walking point corresponds to room, return the access point of the floor on which the room is.
        final Coordinates coordinates1 = new Coordinates(coordinates[0], coordinates[1]);
        final WalkingPoint wantedPoint = new WalkingPoint(coordinates1, floorCode, null, null);
        Optional<WalkingPoint> optionalWalkingPoints = walkingPointList.stream().filter(new Predicate<WalkingPoint>() {
            @Override
            public boolean test(WalkingPoint walkingPoint) {
                return wantedPoint.equals(walkingPoint);
            }
        }).findFirst();
        if(optionalWalkingPoints.isPresent()){
            return optionalWalkingPoints.get();
        }else {
            return null;
        }
    }

    private void populateWalkingPointMap(List<WalkingPoint> walkingPoints) {
        for (WalkingPoint walkingPoint : walkingPoints) {
            walkingPointMap.put(new Integer(walkingPoint.getId()), walkingPoint);
        }
    }

    public double getHeuristicEstimate(WalkingPoint currentCoordinate) {
        double heuristic;
        //check type of working point
        if (currentCoordinate.getFloorCode() == destinationGoal.getFloorCode()) {
            heuristic =
                    getBirdViewDistanceBetweenWalkingPoints(currentCoordinate, destinationGoal);
        } else {
            WalkingPoint accessPoint = getNearestAccessPointForFloor(currentCoordinate);
            heuristic = getBirdViewDistanceBetweenWalkingPoints(currentCoordinate, accessPoint) + getBirdViewDistanceBetweenWalkingPoints(accessPoint, destinationGoal);
        }

        return heuristic;
    }

    public double getF(WalkingPointNode currentCoordinate) {
        return currentCoordinate.getCost() + getHeuristicEstimate(currentCoordinate.getWalkingPoint());
    }

    private WalkingPoint getNearestAccessPointForFloor(WalkingPoint currentPoint) {
        List<WalkingPoint> accessPtList = AppDatabase.getInstance(context).walkingPointDao().getAllAccessPointsOnFloor(currentPoint.getFloorCode(), PointType.ELEVATOR);

        WalkingPoint closestPoint = null;
        if (!accessPtList.isEmpty()) {
            closestPoint = accessPtList.get(0);
            for (int i = 1; i < accessPtList.size(); i++) {
                double closestPointDistance = getBirdViewDistanceBetweenWalkingPoints(currentPoint, closestPoint);
                double otherPointDistance = getBirdViewDistanceBetweenWalkingPoints(currentPoint, accessPtList.get(i));
                closestPoint = closestPointDistance > otherPointDistance ? accessPtList.get(i) : closestPoint;
            }
        }
        return closestPoint;
    }

    public double getBirdViewDistanceBetweenWalkingPoints(WalkingPoint firstCoordinate, WalkingPoint secondCoordinate) {
        return Math.sqrt(
                Math.pow(Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0) - secondCoordinate.getCoordinate().toListDouble().get(0)), 2)
                        + Math.pow(Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1) - secondCoordinate.getCoordinate().toListDouble().get(1)), 2));
    }

    public List<WalkingPoint> searchPathToDestination() {
        walkingPointsToVisit.add(new WalkingPointNode(initialCoordinate, null, getHeuristicEstimate(initialCoordinate), 0));

        while (!walkingPointsToVisit.isEmpty()) {

            WalkingPointNode currentLocation = walkingPointsToVisit.poll();

            if (isGoal(currentLocation.getWalkingPoint())) {
                goalNode = currentLocation;
                return getSolutionPath();
            }

            walkingPointsVisited.put(currentLocation.getWalkingPoint(), currentLocation.getCost());
            addNearestWalkingPoints(currentLocation);
        }
        System.out.print("bordel no goal found :(");
        goalNode = null;
        return null;
    }

    public List<WalkingPoint> getSolutionPath() {
        List<WalkingPoint> solutionPath = new ArrayList<>();
        WalkingPointNode node = goalNode;
        do {
            solutionPath.add(node.getWalkingPoint());
            node = node.parent;
        } while (node != null);
        return solutionPath;
    }

    private void addNearestWalkingPoints(WalkingPointNode currentLocation) {
        for (int id : currentLocation.getWalkingPoint().getConnectedPointsId()) {
            walkingPointsToVisit.add(new WalkingPointNode(walkingPointMap.get(id), currentLocation, getHeuristicEstimate(walkingPointMap.get(id)), getBirdViewDistanceBetweenWalkingPoints(currentLocation.getWalkingPoint(), walkingPointMap.get(id))));
        }
    }

    private boolean isGoal(WalkingPoint currentLocation) {
        return destinationGoal.equals(currentLocation);
    }

    public class WalkingPointComparator implements Comparator<WalkingPointNode> {
        @Override
        public int compare(WalkingPointNode o1, WalkingPointNode o2) {
            if (getF(o1) < getF(o2)) {
                return -1;
            }
            if (getF(o1) > getF(o2)) {
                return 1;
            }
            return 0;
        }
    }

}


