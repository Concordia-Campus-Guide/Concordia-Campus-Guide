package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private List<Direction> directions;

    public Route() {
        this.directions = new ArrayList<>();
    }

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public void addDirection(Direction direction){
        directions.add(direction);
    }

    public List<Direction> getDirections() {
        return directions;
    }
}
