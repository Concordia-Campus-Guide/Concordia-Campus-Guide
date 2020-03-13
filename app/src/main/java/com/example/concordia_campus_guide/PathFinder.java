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
        Comparator<WalkingPointNode> comparator = new WalkingPointComparator();
        this.walkingPointsToVisit = new PriorityQueue<WalkingPointNode>(comparator);
        this.initialCoordinate = initialCoordinate;
        populateWalkingPointMap(walkingPoints);
    }

    private void populateWalkingPointMap(List<WalkingPoint> walkingPoints) {
        for(WalkingPoint walkingPoint : walkingPoints ){
            walkingPointMap.put(walkingPoint.getId(), walkingPoint);
        }
    }

    public double getHeuristicEstimate(WalkingPoint currentCoordinate) {
        double heuristic;
        //check type of working point
        if (currentCoordinate.getFloorCode() == destinationGoal.getFloorCode()){
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
        return null;
    }

    public double getBirdViewDistanceBetweenWalkingPoints(WalkingPoint firstCoordinate, WalkingPoint secondCoordinate){
        return Math.sqrt(
                Math.pow(Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0) - secondCoordinate.getCoordinate().toListDouble().get(0)), 2)
                        + Math.pow(Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1) - secondCoordinate.getCoordinate().toListDouble().get(1)), 2));
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
        for(int id : currentLocation.getConnectedPointsId()){
            walkingPointsToVisit.add(new WalkingPointNode(walkingPointMap.get(id), currentLocation, getHeuristicEstimate(walkingPointMap.get(id)), getBirdViewDistanceBetweenWalkingPoints(currentLocation, walkingPointMap.get(id))));
        }
    }

    private boolean isGoal(WalkingPoint currentLocation) {
        return destinationGoal.equals(currentLocation);
    }
    public class WalkingPointComparator implements Comparator<WalkingPointNode> {
        @Override
        public int compare(WalkingPointNode o1, WalkingPointNode o2) {
            if (getHeuristicEstimate(o1.getWalkingPoint()) < getHeuristicEstimate(o2.getWalkingPoint())) {
                return -1;
            }
            if (getHeuristicEstimate(o1.getWalkingPoint()) > getHeuristicEstimate(o2.getWalkingPoint())) {
                return 1;
            }
            return 0;
        }
    }

}


