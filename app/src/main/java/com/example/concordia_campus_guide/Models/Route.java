package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {

    private List<Direction> directions;

    public Route() {}

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public void addDirection(Direction direction){
        directions.add(direction);
    }
}
