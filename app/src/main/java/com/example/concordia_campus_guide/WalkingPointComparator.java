package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.Comparator;

    public class WalkingPointComparator implements Comparator<WalkingPointNode> {
    @Override
    public int compare(WalkingPointNode o1, WalkingPointNode o2) {
        if (PathFinder.getHeuristicEstimate(o1.getWalkingPoint()) < PathFinder.getHeuristicEstimate(o2.getWalkingPoint())) {
            return -1;
        }
        if (PathFinder.getHeuristicEstimate(o1.getWalkingPoint()) > PathFinder.getHeuristicEstimate(o2.getWalkingPoint())) {
            return 1;
        }
        return 0;
    }
}
