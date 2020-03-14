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

    HashMap<Integer, WalkingPointNode> walkingPointNodesMap;
    PriorityQueue<WalkingPointNode> walkingPointsToVisit;
    HashMap<WalkingPointNode, Double> walkingPointsVisited;

    WalkingPoint initialPoint;
    WalkingPoint destinationPoint;
    Context context;

    public PathFinder(Context context, RoomModel source, RoomModel destination) {
        this.walkingPointsToVisit = new PriorityQueue<>(new WalkingPointComparator());
        this.walkingPointsVisited = new HashMap<>();

        List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
        walkingPointNodesMap = populateWalkingPointMap(walkingPoints);

        this.initialPoint = getWalkingPoint(source.getCenterCoordinates(), source.getFloorCode(), walkingPoints);
        this.destinationPoint = getWalkingPoint(destination.getCenterCoordinates(), destination.getFloorCode(), walkingPoints);

        searchPathToDestination();
    }

    private HashMap<Integer, WalkingPointNode> populateWalkingPointMap(List<WalkingPoint> walkingPoints) {
        this.walkingPointNodesMap = new HashMap<>();
        for (WalkingPoint walkingPoint : walkingPoints) {
            walkingPointNodesMap.put(new Integer(walkingPoint.getId()), new WalkingPointNode(walkingPoint, null, 0, 0));
        }
        return walkingPointNodesMap;
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

    public List<WalkingPoint> searchPathToDestination() {

        addInitialPointToMap();

        while (!walkingPointsToVisit.isEmpty()) {

            WalkingPointNode currentLocation = walkingPointsToVisit.poll();

            if (walkingPointsVisited.containsKey(currentLocation))
                continue;
            else
                walkingPointsVisited.put(currentLocation, currentLocation.getCost());

            if (isGoal(currentLocation.getWalkingPoint())) {
                return getSolutionPath(currentLocation);
            }
            addNearestWalkingPoints(currentLocation);
        }
        return null;
    }

    private void addInitialPointToMap() {
        WalkingPointNode initial = walkingPointNodesMap.get(initialPoint.getId());
        initial.setHeuristic(getHeuristicEstimate(initial.getWalkingPoint()));
        walkingPointsToVisit.add(initial);
    }

    private boolean isGoal(WalkingPoint currentLocation) {
        return destinationPoint.equals(currentLocation);
    }

    public List<WalkingPoint> getSolutionPath(WalkingPointNode goalNode) {
        List<WalkingPoint> solutionPath = new ArrayList<>();

        do {
            solutionPath.add(goalNode.getWalkingPoint());
            goalNode = goalNode.parent;
        } while (goalNode != null);

        return solutionPath;
    }


    private void addNearestWalkingPoints(WalkingPointNode currentLocation) {
        for (int id : currentLocation.getWalkingPoint().getConnectedPointsId()) {

            WalkingPointNode child = walkingPointNodesMap.get(id);
            double c = currentLocation.getCost()+ getEuclideanDistance(currentLocation.getWalkingPoint(), walkingPointNodesMap.get(id).getWalkingPoint());


            if (walkingPointsVisited.containsKey(child)) {
                if(child.getCost() > c){
                    child.setParent(currentLocation);
                    child.setCost(c);
                }
                continue;
            }

            else if(c < child.getCost() && child.getCost() > 0 || child.getCost() == 0){
                child.setParent(currentLocation);
                child.setHeuristic(getHeuristicEstimate(child.getWalkingPoint()));
                child.setCost(c);
                walkingPointsToVisit.add(child);
            }
        }
    }

    public double getEuclideanDistance(WalkingPoint firstCoordinate, WalkingPoint secondCoordinate) {

        double resultLatDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0) - secondCoordinate.getCoordinate().toListDouble().get(0));
        double resultLongDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1) - secondCoordinate.getCoordinate().toListDouble().get(1));

        return Math.sqrt(Math.pow(resultLatDiff, 2) + Math.pow(resultLongDiff, 2));
    }

    public double getF(WalkingPointNode currentCoordinate) {
        return currentCoordinate.getCost() + getHeuristicEstimate(currentCoordinate.getWalkingPoint());
    }

    public double getHeuristicEstimate(WalkingPoint currentCoordinate) {
        double heuristic;
        //check type of working point
        if (currentCoordinate.getFloorCode().equalsIgnoreCase(destinationPoint.getFloorCode())) {
            heuristic =
                    getEuclideanDistance(currentCoordinate, destinationPoint);
        } else {
            WalkingPoint accessPoint = getNearestAccessPointForFloor(currentCoordinate);
            heuristic = getEuclideanDistance(currentCoordinate, accessPoint) + getEuclideanDistance(accessPoint, destinationPoint);
        }

        return heuristic;
    }

    private WalkingPoint getNearestAccessPointForFloor(WalkingPoint currentPoint) {
        List<WalkingPoint> accessPtList = AppDatabase.getInstance(context).walkingPointDao().getAllAccessPointsOnFloor(currentPoint.getFloorCode(), PointType.ELEVATOR);

        WalkingPoint closestPoint = null;
        if (!accessPtList.isEmpty()) {
            closestPoint = accessPtList.get(0);
            for (int i = 1; i < accessPtList.size(); i++) {
                double closestPointDistance = getEuclideanDistance(currentPoint, closestPoint);
                double otherPointDistance = getEuclideanDistance(currentPoint, accessPtList.get(i));
                closestPoint = closestPointDistance > otherPointDistance ? accessPtList.get(i) : closestPoint;
            }
        }
        return closestPoint;
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


