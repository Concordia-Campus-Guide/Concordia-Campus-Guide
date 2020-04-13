package com.example.concordia_campus_guide.helper;

import android.content.SharedPreferences;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Floor;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Usage Example:
 * Double[] src = {-73.5791637, 45.49526596};
 * Double[] trg = {-73.57908558, 45.49735711};
 * RoomModel room1 = new RoomModel(src, "437", "MB-S2");
 * RoomModel room2 = new RoomModel(trg, "838", "H-8");
 * PathFinder finder = new PathFinder(AppDatabase.getInstance(getContext()), room1, room2);
 * List<WalkingPoint> solution = finder.getPathToDestination();
 */
public class PathFinder {

    private HashMap<Integer, WalkingPointNode> walkingPointNodesMap;

    public PriorityQueue<WalkingPointNode> getWalkingPointsToVisit() {
        return walkingPointsToVisit;
    }

    private final PriorityQueue<WalkingPointNode> walkingPointsToVisit;
    private final HashMap<WalkingPointNode, Double> walkingPointsVisited;

    private final WalkingPoint initialPoint;
    private final WalkingPoint destinationPoint;
    private final IndoorPathHeuristic indoorPathHeuristic;
    private final AppDatabase appDatabase;
    private boolean isStaffAccessiblity;
    private boolean isReducedAccessiblity;

    public PathFinder(final AppDatabase appDatabase, SharedPreferences accessibilityPreferences, final Place source, final Place destination) {
        this.walkingPointsToVisit = new PriorityQueue<>(new WalkingPointComparator());
        this.walkingPointsVisited = new HashMap<>();

        this.isStaffAccessiblity = accessibilityPreferences.getBoolean(ClassConstants.STAFF_TOGGLE, false);
        this.isReducedAccessiblity = accessibilityPreferences.getBoolean(ClassConstants.ACCESSIBILITY_TOGGLE, false);
        this.appDatabase = appDatabase;
        this.indoorPathHeuristic = new IndoorPathHeuristic(appDatabase, isStaffAccessiblity, isReducedAccessiblity);

        final List<WalkingPoint> walkingPoints = appDatabase.walkingPointDao().getAll();
        walkingPointNodesMap = (HashMap<Integer, WalkingPointNode>) populateWalkingPointMap(walkingPoints);

        this.initialPoint = getWalkingPointCorrespondingToPlace(source);
        this.destinationPoint = getWalkingPointCorrespondingToPlace(destination);
    }

    /**
     * This method's purpose is to populate our map (graph) with all the walking points in the map.
     *
     * @param walkingPoints
     * @return
     */
    public Map<Integer, WalkingPointNode> populateWalkingPointMap(final List<WalkingPoint> walkingPoints) {
        this.walkingPointNodesMap = new HashMap<>();
        for (final WalkingPoint walkingPoint : walkingPoints) {
            walkingPointNodesMap.put(walkingPoint.getId(), new WalkingPointNode(walkingPoint, null, 0, 0));
        }
        return walkingPointNodesMap;
    }

    public WalkingPoint getWalkingPointCorrespondingToPlace(final Place place) {
        String placeCode;
        String floorCode;

        WalkingPoint point = null;
        if (place instanceof Building) {
            String entranceFloor = ((Building) place).getBuildingCode() + "-" + ((Building) place).getEntranceFloor();
            point = appDatabase.walkingPointDao().getWalkingPoint(entranceFloor, "entrance");
        } else if (place instanceof RoomModel) {
            //placeCode is not unique for Rooms, therefore we need to fetch the Walking point by searching for both floor code and place code
            floorCode = ((RoomModel) place).getFloorCode();
            placeCode = ((RoomModel) place).getRoomCode();
            point = appDatabase.walkingPointDao().getWalkingPoint(floorCode, placeCode);
        } else if (place instanceof Floor) {
            floorCode = ((Floor) place).getFloorCode();
            List<WalkingPoint> allElevators = appDatabase.walkingPointDao().getAllAccessPointsOnFloor(floorCode, PointType.ELEVATOR);
            point = allElevators != null && !allElevators.isEmpty()? allElevators.get(0):null;
        }

        return point;
    }

    /**
     * This method's purpose is to find a solution path from a point A to a point B. It will use a priorityQueue to get the best next point to visit at each moment. If the point was already visited, it will skip it.
     *
     * @return
     */
    public List<WalkingPoint> getPathToDestination() {

        addInitialPointToMap();

        while (!walkingPointsToVisit.isEmpty()) {
            final WalkingPointNode currentLocation = walkingPointsToVisit.poll();

            if (walkingPointsVisited.containsKey(currentLocation) || !isWalkingPointAccessible(currentLocation.getWalkingPoint()))
                continue;
            else
                walkingPointsVisited.put(currentLocation, currentLocation.getCost());

            if (isGoal(currentLocation.getWalkingPoint())) {
                return getSolutionPath(currentLocation);
            }
            addNearestWalkingPoints(currentLocation);
        }
        return new ArrayList<>();
    }

    private boolean isWalkingPointAccessible(WalkingPoint point){
        String pointType = point.getPointType();
        switch (pointType){
            case PointType.STAIRS:
                return !isReducedAccessiblity;
            case PointType.STAFF_ELEVATOR:
            case PointType.STAFF_WASHROOM:
                return isStaffAccessiblity;
            default:
                return true;
        }
    }

    /**
     * This helper method simply adds the initial point into the priorityQueue.
     */
    private void addInitialPointToMap() {
        final WalkingPointNode initial = walkingPointNodesMap.get(initialPoint.getId());
        initial.setHeuristic(indoorPathHeuristic.computeHeuristic(initialPoint, destinationPoint));
        walkingPointsToVisit.add(initial);
    }

    public boolean isGoal(final WalkingPoint currentLocation) {
        return destinationPoint.equals(currentLocation);
    }

    /**
     * This method will take care of setting the solution path once a solution has been found.
     *
     * @param goalNode
     * @return
     */
    protected List<WalkingPoint> getSolutionPath(WalkingPointNode goalNode) {
        final List<WalkingPoint> solutionPath = new ArrayList<>();
        do {
            solutionPath.add(goalNode.getWalkingPoint());
            goalNode = goalNode.parent;
        } while (goalNode != null);

        Collections.reverse(solutionPath);
        return solutionPath;
    }

    /**
     * This method's purpose is to add the reachable points from a walkingPoint. If one of these points is already in the open list (nodes to visit list) with a higher cost,
     * we will update it. Same if it was in the closed list(visited nodes list), we will update the cost only, in this class.
     *
     * @param currentNode
     */
    public void addNearestWalkingPoints(final WalkingPointNode currentNode) {
        for (final int id : currentNode.getWalkingPoint().getConnectedPointsId()) {

            final WalkingPointNode adjacentNode = walkingPointNodesMap.get(id);
            final double currentCost = currentNode.getCost() + indoorPathHeuristic.getEuclideanDistance(currentNode.getWalkingPoint(),
                    adjacentNode.getWalkingPoint());
            final double previousCost = adjacentNode.getCost();

            if (walkingPointsVisited.containsKey(adjacentNode)) {
                if (currentCost < previousCost) {
                    updateWalkingNode(adjacentNode, currentNode, currentCost);
                }
            } else if (currentCost < previousCost && previousCost > 0 || previousCost == 0) {
                updateWalkingNode(adjacentNode, currentNode, currentCost);
                walkingPointsToVisit.add(adjacentNode);
            }
        }
    }

    protected void updateWalkingNode(final WalkingPointNode node, final WalkingPointNode parent, final double cost) {
        node.setParent(parent);
        node.setCost(cost);
        if (node.heuristic == 0)
            node.setHeuristic(indoorPathHeuristic.computeHeuristic(node.getWalkingPoint(), destinationPoint));
    }


    protected double computeEstimatedCostFromInitialToDestination(final WalkingPointNode currentCoordinate) {
        return currentCoordinate.getCost() + indoorPathHeuristic.computeHeuristic(currentCoordinate.getWalkingPoint(), destinationPoint);
    }


    /**
     * This is a comparator object that will compare our Walking point based on a heuristic for a priorityQueue.
     */
    public class WalkingPointComparator implements Comparator<WalkingPointNode> {
        @Override
        public int compare(final WalkingPointNode o1, final WalkingPointNode o2) {
            if (computeEstimatedCostFromInitialToDestination(o1) < computeEstimatedCostFromInitialToDestination(o2)) {
                return -1;
            }
            if (computeEstimatedCostFromInitialToDestination(o1) > computeEstimatedCostFromInitialToDestination(o2)) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * This is a wrapper class for the WalkingPoint object that represents a specific node in our graph, with a parent, cost and heuristic.
     */
    public class WalkingPointNode {
        WalkingPoint walkingPoint;
        WalkingPointNode parent;
        double heuristic;
        double cost;

        public WalkingPointNode(final WalkingPoint walkingPoint, final WalkingPointNode parent, final double heuristic,
                                final double cost) {
            this.walkingPoint = walkingPoint;
            this.parent = parent;
            this.heuristic = heuristic;
            this.cost = cost;
        }

        public WalkingPoint getWalkingPoint() {
            return walkingPoint;
        }

        public void setWalkingPoint(final WalkingPoint walkingPoint) {
            this.walkingPoint = walkingPoint;
        }

        public WalkingPointNode getParent() {
            return parent;
        }

        public void setParent(final WalkingPointNode parent) {
            this.parent = parent;
        }

        public double getHeuristic() {
            return heuristic;
        }

        public void setHeuristic(final double heuristic) {
            this.heuristic = heuristic;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(final double cost) {
            this.cost = cost;
        }
    }
}

