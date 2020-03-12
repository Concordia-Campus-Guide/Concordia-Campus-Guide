package com.example.concordia_campus_guide.Global;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndoorSearchGraph {
    private Map<WalkingPoint, List<WalkingPoint>> adjacencyList;

    public void addVertex(Coordinates coordinates, String floorCode,  ListOfCoordinates connectedPoints) {
        WalkingPoint newPoint = new WalkingPoint(coordinates, floorCode,connectedPoints);
        if (!adjacencyList.containsKey(newPoint)) {
            adjacencyList.put(newPoint, new ArrayList<WalkingPoint>());
        }
    }

    //TODO: Create removeVertex Method when needed

    public void addEdge(WalkingPoint point1, WalkingPoint point2){
        adjacencyList.get(point1).add(point2);
    }
    public void addEdge(WalkingPoint point1, WalkingPoint point2, Double cost){
        adjacencyList.get(point1).add(point2);
    }

}
