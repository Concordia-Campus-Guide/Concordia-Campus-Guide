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

/**
 * Usage Example:
 * Double[] src = {-73.57883681, 45.49731974};
 * Double[] trg = {-73.57873723, 45.49748425};
 * RoomModel room1 = new RoomModel(src,"H927" ,"H-9");
 * RoomModel room2 = new RoomModel(trg,"H842", "H-8");
 * PathFinder finder = new PathFinder(getContext(),  room1, room2);
 * List<WalkingPoint> solution = finder.getPathToDestination();
 */
public class PathFinder {

    private HashMap<Integer, WalkingPointNode> walkingPointNodesMap;
    private PriorityQueue<WalkingPointNode> walkingPointsToVisit;
    private HashMap<WalkingPointNode, Double> walkingPointsVisited;

    private WalkingPoint initialPoint;
    private WalkingPoint destinationPoint;
    private Context context;

    public PathFinder(Context context, RoomModel source, RoomModel destination) {
        this.walkingPointsToVisit = new PriorityQueue<>(new WalkingPointComparator());
        this.walkingPointsVisited = new HashMap<>();

        List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
        walkingPointNodesMap = populateWalkingPointMap(walkingPoints);

        this.context = context;
        this.initialPoint = getWalkingPointCorrespondingToRoom(source, walkingPoints);
        this.destinationPoint = getWalkingPointCorrespondingToRoom(destination, walkingPoints);
    }

    protected HashMap<Integer, WalkingPointNode> populateWalkingPointMap(List<WalkingPoint> walkingPoints) {
        this.walkingPointNodesMap = new HashMap<>();
        for (WalkingPoint walkingPoint : walkingPoints) {
            walkingPointNodesMap.put(new Integer(walkingPoint.getId()), new WalkingPointNode(walkingPoint, null, 0, 0));
        }
        return walkingPointNodesMap;
    }

    protected WalkingPoint getWalkingPointCorrespondingToRoom(RoomModel room, List<WalkingPoint> walkingPointList) {
        //TODO: If no walking point corresponds to room, return the access point of the floor on which the room is.
        final Coordinates coordinates1 = new Coordinates(room.getCenterCoordinates()[0], room.getCenterCoordinates()[1]);
        final WalkingPoint wantedPoint = new WalkingPoint(coordinates1, room.getFloorCode(), null, null);

        Optional<WalkingPoint> optionalWalkingPoints = walkingPointList.stream().filter(new Predicate<WalkingPoint>() {
            @Override
            public boolean test(WalkingPoint walkingPoint) {
                return wantedPoint.equals(walkingPoint);
            }
        }).findFirst();

        return optionalWalkingPoints.isPresent() ? optionalWalkingPoints.get() : null;
    }

    public List<WalkingPoint> getPathToDestination() {

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
        initial.setHeuristic(computeHeuristicEstimate(initialPoint));
        walkingPointsToVisit.add(initial);
    }

    protected boolean isGoal(WalkingPoint currentLocation) {
        return destinationPoint.equals(currentLocation);
    }

    protected List<WalkingPoint> getSolutionPath(WalkingPointNode goalNode) {
        List<WalkingPoint> solutionPath = new ArrayList<>();
        do {
            solutionPath.add(goalNode.getWalkingPoint());
            goalNode = goalNode.parent;
        } while (goalNode != null);

        return solutionPath;
    }

    protected void addNearestWalkingPoints(WalkingPointNode currentNode) {
        for (int id : currentNode.getWalkingPoint().getConnectedPointsId()) {

            WalkingPointNode adjacentNode = walkingPointNodesMap.get(id);
            double currentCost = currentNode.getCost() + getEuclideanDistance(currentNode.getWalkingPoint(), walkingPointNodesMap.get(id).getWalkingPoint());
            double previousCost = adjacentNode.getCost();

            if (walkingPointsVisited.containsKey(adjacentNode)) {
                if (currentCost < previousCost) {
                    updateWalkingNode(adjacentNode, currentNode, currentCost);
                }
                continue;
            } else if (currentCost < previousCost && previousCost > 0 || previousCost == 0) {
                updateWalkingNode(adjacentNode, currentNode, currentCost);
                walkingPointsToVisit.add(adjacentNode);
            }
        }
    }

    protected void updateWalkingNode(WalkingPointNode node, WalkingPointNode parent, double cost) {
        node.setParent(parent);
        node.setCost(cost);
        if (node.heuristic == 0)
            node.setHeuristic(computeHeuristicEstimate(node.getWalkingPoint()));
    }

    protected double getEuclideanDistance(WalkingPoint firstCoordinate, WalkingPoint secondCoordinate) {
        double resultLatDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0) - secondCoordinate.getCoordinate().toListDouble().get(0));
        double resultLongDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1) - secondCoordinate.getCoordinate().toListDouble().get(1));

        return Math.sqrt(Math.pow(resultLatDiff, 2) + Math.pow(resultLongDiff, 2));
    }

    protected double computeEstimatedCostFromInitialToDestination(WalkingPointNode currentCoordinate) {
        return currentCoordinate.getCost() + computeHeuristicEstimate(currentCoordinate.getWalkingPoint());
    }

    protected double computeHeuristicEstimate(WalkingPoint currentCoordinate) {
        if (!currentCoordinate.getFloorCode().equalsIgnoreCase(destinationPoint.getFloorCode())) {
            WalkingPoint accessPoint = getNearestAccessPointForFloor(currentCoordinate);
            return getEuclideanDistance(currentCoordinate, accessPoint) + getEuclideanDistance(accessPoint, destinationPoint);
        }

        return getEuclideanDistance(currentCoordinate, destinationPoint);
    }

    protected WalkingPoint getNearestAccessPointForFloor(WalkingPoint currentPoint) {
        List<WalkingPoint> accessPtList = AppDatabase.getInstance(context).walkingPointDao().getAllAccessPointsOnFloor(currentPoint.getFloorCode(), PointType.ELEVATOR);
        return getClosestPointToCurrentPointFromList(currentPoint, accessPtList);
    }

    protected WalkingPoint getClosestPointToCurrentPointFromList(WalkingPoint currentPoint, List<WalkingPoint> accessPtList) {
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

    public HashMap<Integer, WalkingPointNode> getWalkingPointNodesMap() {
        return walkingPointNodesMap;
    }

    public PriorityQueue<WalkingPointNode> getWalkingPointsToVisit() {
        return walkingPointsToVisit;
    }

    public HashMap<WalkingPointNode, Double> getWalkingPointsVisited() {
        return walkingPointsVisited;
    }

    public WalkingPoint getInitialPoint() {
        return initialPoint;
    }

    public WalkingPoint getDestinationPoint() {
        return destinationPoint;
    }

    public class WalkingPointComparator implements Comparator<WalkingPointNode> {
        @Override
        public int compare(WalkingPointNode o1, WalkingPointNode o2) {
            if (computeEstimatedCostFromInitialToDestination(o1) < computeEstimatedCostFromInitialToDestination(o2)) {
                return -1;
            }
            if (computeEstimatedCostFromInitialToDestination(o1) > computeEstimatedCostFromInitialToDestination(o2)) {
                return 1;
            }
            return 0;
        }
    }
}


