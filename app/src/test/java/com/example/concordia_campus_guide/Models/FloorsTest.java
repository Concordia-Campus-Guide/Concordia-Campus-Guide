package com.example.concordia_campus_guide.Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FloorsTest {
    Floors floors;

    Coordinates coordinates = new Coordinates(45.4972685,-73.5789475);
    String floorCode = "H-9";
    float altitude = 0;
    String campus = "SGW";

    Floor floorWithArgs = new Floor(coordinates, floorCode, altitude);;
    Floor floorWithCampus = new Floor(coordinates, floorCode, altitude, campus);


    List<Floor> listOfFloors = new ArrayList<Floor>(){{
        add(floorWithArgs);
        add(floorWithCampus);
        }
    };


    @Before
    public void init(){
        floors = new Floors();
        floorWithArgs = new Floor(coordinates, floorCode, altitude);;
        floorWithCampus = new Floor(coordinates, floorCode, altitude, campus);
    }


    @Test
    public void getFloors() {
        assertTrue(floors.getFloors().isEmpty());
    }

    @Test
    public void setFloors(){
        assertTrue(floors.getFloors().isEmpty());
        floors.setFloors(listOfFloors);
        assertFalse(floors.getFloors().isEmpty());
    }

    @Test
    public void getPlaces() {
        floors.setFloors(listOfFloors);
        List<Place> places = new ArrayList<Place>();
        places = floors.getPlaces();
        assertEquals(2, places.size());
    }
}