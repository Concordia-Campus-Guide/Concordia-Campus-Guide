package com.example.concordia_campus_guide.Helper;

import android.content.Context;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final PriorityQueue<WalkingPointNode> walkingPointsToVisit;
    private final HashMap<WalkingPointNode, Double> walkingPointsVisited;

    private final WalkingPoint initialPoint;
    private final WalkingPoint destinationPoint;
    private final Context context;
    private final IndoorPathHeuristic indoorPathHeuristic;

    public PathFinder(final Context context, final RoomModel source, final RoomModel destination) {
        this.walkingPointsToVisit = new PriorityQueue<>(new WalkingPointComparator());
        this.walkingPointsVisited = new HashMap<>();
        this.indoorPathHeuristic = new IndoorPathHeuristic(context);

        final List<WalkingPoint> walkingPoints = AppDatabase.getInstance(context).walkingPointDao().getAll();
        walkingPointNodesMap = populateWalkingPointMap(walkingPoints);

        this.context = context;
        this.initialPoint = getWalkingPointCorrespondingToRoom(source, walkingPoints);
        this.destinationPoint = getWalkingPointCorrespondingToRoom(destination, walkingPoints);
    }

    /**
     *  This methods' purpose is to populate our map (graph) with all the walking points in the map.
     * @param walkingPoints
     * @return
     */
    protected HashMap<Integer, WalkingPointNode> populateWalkingPointMap(final List<WalkingPoint> walkingPoints) {
        this.walkingPointNodesMap = new HashMap<>();
        for (final WalkingPoint walkingPoint : walkingPoints) {
            walkingPointNodesMap.put(walkingPoint.getId(), new WalkingPointNode(walkingPoint, null, 0, 0));
        }
        return walkingPointNodesMap;
    }

    /**
     * This methods' purpose is to get a corresponding walking point given a room object.
     * @param room
     * @param walkingPointList
     * @return
     */
    protected WalkingPoint getWalkingPointCorrespondingToRoom(final RoomModel room,
            final List<WalkingPoint> walkingPointList) {
        final Coordinates coordinates1 = new Coordinates(room.getCenterCoordinates()[0],
                room.getCenterCoordinates()[1]);
        final WalkingPoint wantedPoint = new WalkingPoint(coordinates1, room.getFloorCode(), null, null);

        final Optional<WalkingPoint> optionalWalkingPoints = walkingPointList.stream()
                .filter(new Predicate<WalkingPoint>() {
                    @Override
                    public boolean test(final WalkingPoint walkingPoint) {
                        return wantedPoint.equals(walkingPoint);
                    }
                }).findFirst();

        return optionalWalkingPoints.isPresent() ? optionalWalkingPoints.get() : null;
    }

    /**
     * This methods' purpose is to find a solution path from a point A to a point B. It will use a priorityQueue to get the best next point to visit at each moment. If the point was already visited, it will skip it.
     * @return
     */
    public List<WalkingPoint> getPathToDestination() {

        addInitialPointToMap();

        while (!walkingPointsToVisit.isEmpty()) {
            final WalkingPointNode currentLocation = walkingPointsToVisit.poll();

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

    /**
     * This helper methods simply add the first source point into the priorityQueue.
     */
    private void addInitialPointToMap() {
        final WalkingPointNode initial = walkingPointNodesMap.get(initialPoint.getId());
        initial.setHeuristic(indoorPathHeuristic.computeHeuristic(initialPoint, destinationPoint));
        walkingPointsToVisit.add(initial);
    }

    protected boolean isGoal(final WalkingPoint currentLocation) {
        return destinationPoint.equals(currentLocation);
    }

    /**
     * This method will take care of setting the solution path once a solution has been found.
     * @param goalNode
     * @return
     */
    protected List<WalkingPoint> getSolutionPath(WalkingPointNode goalNode) {
        final List<WalkingPoint> solutionPath = new ArrayList<>();
        do {
            solutionPath.add(goalNode.getWalkingPoint());
            goalNode = goalNode.parent;
        } while (goalNode != null);

        return solutionPath;
    }

    /**
     * This methods' purpose is to add the reachable points from a walkingPoint. If one of these points is already in the open list with a higher cost,
     * we will update it. Same if it was in the closed list, we will update the cost only, in this class.
     * @param currentNode
     */
    protected void addNearestWalkingPoints(final WalkingPointNode currentNode) {
        for (final int id : currentNode.getWalkingPoint().getConnectedPointsId()) {

            final WalkingPointNode adjacentNode = walkingPointNodesMap.get(id);
            final double currentCost = currentNode.getCost() + indoorPathHeuristic.getEuclideanDistance(currentNode.getWalkingPoint(),
                    walkingPointNodesMap.get(id).getWalkingPoint());
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

    public Map<Integer, WalkingPointNode> getWalkingPointNodesMap() {
        return walkingPointNodesMap;
    }

    public PriorityQueue<WalkingPointNode> getWalkingPointsToVisit() {
        return walkingPointsToVisit;
    }

    public Map<WalkingPointNode, Double> getWalkingPointsVisited() {
        return walkingPointsVisited;
    }

    public WalkingPoint getInitialPoint() {
        return initialPoint;
    }

    public WalkingPoint getDestinationPoint() {
        return destinationPoint;
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
     * This is a wrapper class for the WalkingPoint object that reprent a specific node in our graph, with a parent, cost and heuristic.
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


