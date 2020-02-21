package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Buildings {
    private List<BuildingInfo> buildings;

    public  Buildings(){
        buildings = new ArrayList<BuildingInfo>();
    }
    public List<BuildingInfo> getBuildings() {
        return buildings;
    }

    public Buildings(List<BuildingInfo> buildings) {
        this.buildings = buildings;
    }

    public void setBuildings(List<BuildingInfo> buildings) {
        this.buildings = buildings;
    }
}