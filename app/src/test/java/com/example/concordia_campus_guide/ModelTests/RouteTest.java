package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Route;
import com.example.concordia_campus_guide.Models.TransitType;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class RouteTest {
    private Route route;
    List<Direction> directions;

    @Before
    public void init() {
        Direction direction1 = new Direction(new LatLng(-73.57907921075821, 45.49702057370776), new LatLng(-73.57921063899994, 45.49707133596979), TransitType.BIKE, "Pulling up with the may bike", 15);
        Direction direction2 = new Direction(new LatLng(-73.57921063899994, 45.49707133596979), new LatLng(-73.57907921075821, 45.49702057370776), TransitType.CAR, "This is a description boom boom vroom", 20);
        directions = new ArrayList<>();
        directions.add(direction1);
        directions.add(direction2);
        route = new Route();
    }

    @Test
    public void addDirectionsTest() {
        assertEquals(route.getDirections(), new ArrayList<Direction>());
        route.addDirection(directions.get(0));
        route.addDirection(directions.get(1));
        assertEquals(route.getDirections(),directions);
    }

    @Test
    public void getDirections(){
        assertTrue(route.getDirections().isEmpty());
        route = new Route(directions);
        assertEquals(directions, route.getDirections());

    }
}
