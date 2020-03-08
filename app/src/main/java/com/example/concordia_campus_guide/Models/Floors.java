package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Floors {

    private List<Floor> Floors;

    public Floors(){
        Floors = new ArrayList<Floor>();
    }

    public List<Floor> getFloors(){
        return Floors;
    }


}
