package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {
    HashMap<Integer, WalkingPoint> walkingPointMap;
    PriorityQueue <WalkingPointNode> walkingPointsToVisit;
    HashMap<WalkingPoint, Double> walkingPointsVisited;
    WalkingPoint initialCoordinate;
    WalkingPoint destinationGoal;

    public PathFinder(List<WalkingPoint> walkingPoints, WalkingPoint destinationGoal, WalkingPoint initialCoordinate){
        this.destinationGoal = destinationGoal;
        Comparator<WalkingPoint> comparator = new WalkingPointComparator();
        this.walkingPointsToVisit = new PriorityQueue<>(comparator);
        this.initialCoordinate = initialCoordinate;
        walkingPointCost = new HashMap<>();
        populateWalkingPointMap(walkingPoints);
    }

    private void populateWalkingPointMap(List<WalkingPoint> walkingPoints) {
        for(WalkingPoint walkingPoint : walkingPoints ){
            walkingPointMap.put(walkingPoint.id, walkingPoint);
        }
    }

    public double getHeuristicEstimate(WalkingPoint currentCoordinate) {
        double heuristic;
        //check type of working point
        if (currentCoordinate.getFloor() == destinationGoal.getFloor()){
            heuristic =
                    getBirdViewDistanceBetweenWalkingPoints(currentCoordinate, destinationGoal);
        }
        else {
            WalkingPoint accessPoint = getAccessPointForFloor();
            heuristic = getBirdViewDistanceBetweenWalkingPoints(currentCoordinate, accessPoint) + getBirdViewDistanceBetweenWalkingPoints(accessPoint, destinationGoal);
        }

        return heuristic;
    }

    public double getF(WalkingPointNode currentCoordinate) {

        return currentCoordinate.getCost() + getHeuristicEstimate(currentCoordinate.getWalkingPoint());
    }
    private WalkingPoint getAccessPointForFloor() {
        //TODO: FUNKY STUFF
    }

    public double getBirdViewDistanceBetweenWalkingPoints(WalkingPoint firstCoordinate, WalkingPoint secondCoordinate){
        return Math.sqrt(
                Math.pow(Math.abs(firstCoordinate.coordinates.toListDouble().get(0) - secondCoordinate.coordinates.toListDouble().get(0)), 2)
                        + Math.pow(Math.abs(firstCoordinate.coordinates.toListDouble().get(1) - secondCoordinate.coordinates.toListDouble().get(1)), 2));
    }

    public void findBestPathToDestinationGoal(){
        walkingPointsToVisit.add(new WalkingPointNode(initialCoordinate, null, getHeuristicEstimate(initialCoordinate), 0));

        while(!walkingPointsToVisit.isEmpty()){

            WalkingPointNode currentLocation = walkingPointsToVisit.poll();

            if(isGoal(currentLocation.getWalkingPoint())){
                return; // set goal state
            }

            walkingPointsVisited.put(currentLocation.getWalkingPoint(), currentLocation.getCost());
            addNearestWalkingPoints(currentLocation.getWalkingPoint());
        }

    }

    private void addNearestWalkingPoints(WalkingPoint currentLocation) {
        for(WalkingPoint walkingPoint : walkingPointMap.get(currentLocation.getId)){
            walkingPointsToVisit.add(new WalkingPointNode(walkingPoint, currentLocation, getHeuristicEstimate(walkingPoint), getBirdViewDistanceBetweenWalkingPoints(currentLocation, walkingPoint)));
        }
    }

    private boolean isGoal(WalkingPoint currentLocation) {
        return destinationGoal.equals(currentLocation);
    }
    public class WalkingPointComparator implements Comparator<WalkingPoint> {
        @Override
        public int compare(WalkingPoint o1, WalkingPoint o2) {
            if (getHeuristicEstimate(o1) < getHeuristicEstimate(o2)) {
                return -1;
            }
            if (getHeuristicEstimate(o1) > getHeuristicEstimate(o2)) {
                return 1;
            }
            return 0;
        }
    }

}


