package com.example.concordia_campus_guide.models;

import java.util.ArrayList;
import java.util.List;

public class Floors {

    private List<Floor> Floors;

    public Floors(){
        Floors = new ArrayList<>();
    }

    public List<Floor> getFloors(){
        return Floors;
    }

    public void setFloors(List<Floor> Floors){
        this.Floors = Floors;
    }

    public List<Place> getPlaces(){
        List<Place> places = new ArrayList<>();
        for(Floor floor: Floors){
            places.add(floor);
        }
        return places;
    }
}
