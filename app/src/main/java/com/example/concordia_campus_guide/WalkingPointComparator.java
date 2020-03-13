package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.Comparator;

    public class WalkingPointComparator implements Comparator<WalkingPoint> {
    @Override
    public int compare(WalkingPoint o1, WalkingPoint o2) {
        if (PathFinder.getHeuristicEstimate(o1) < PathFinder.getHeuristicEstimate(o2)) {
            return -1;
        }
        if (PathFinder.getHeuristicEstimate(o1) > PathFinder.getHeuristicEstimate(o2)) {
            return 1;
        }
        return 0;
    }
}
