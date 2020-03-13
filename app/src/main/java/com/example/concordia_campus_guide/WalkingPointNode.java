package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.Models.WalkingPoint;

public class WalkingPointNode {
    WalkingPoint walkingPoint;
    WalkingPoint parent;
    double heuristic;
    double cost;

    public WalkingPointNode(WalkingPoint walkingPoint, WalkingPoint parent, double heuristic, double cost) {
        this.walkingPoint = walkingPoint;
        this.parent = parent;
        this.heuristic = heuristic;
        this.cost = cost;
    }

    public WalkingPoint getWalkingPoint() {
        return walkingPoint;
    }

    public void setWalkingPoint(WalkingPoint walkingPoint) {
        this.walkingPoint = walkingPoint;
    }

    public WalkingPoint getParent() {
        return parent;
    }

    public void setParent(WalkingPoint parent) {
        this.parent = parent;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
