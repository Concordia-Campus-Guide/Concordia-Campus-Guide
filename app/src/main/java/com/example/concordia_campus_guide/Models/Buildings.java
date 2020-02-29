package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Buildings {

    private List<Building> Buildings;

    public Buildings(){ this.Buildings = new ArrayList<Building>(); }

    public Buildings(List<Building> buildings){ this.Buildings = buildings; }

    public Building getBuilding(String buildingCode){
        for(Building building: Buildings){
            if(building.getBuilding_Code().equals(buildingCode)){
                return building;
            }
        }
        return null;
    }

    public List<Building> getBuildings(){ return Buildings; }

    public void setBuildings(List<Building> buildings){ this.Buildings = buildings; }

}
