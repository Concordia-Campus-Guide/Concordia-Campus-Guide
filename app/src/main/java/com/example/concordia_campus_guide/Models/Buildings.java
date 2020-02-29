package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Buildings {

    private List<BuildingInfo> Buildings;

    public Buildings(){ this.Buildings = new ArrayList<BuildingInfo>(); }

    public Buildings(List<BuildingInfo> buildings){ this.Buildings = buildings; }

    public BuildingInfo getBuilding(String buildingCode){
        for(BuildingInfo building: Buildings){
            if(building.getBuilding_Code().equals(buildingCode)){
                return building;
            }
        }
        return null;
    }

    public List<BuildingInfo> getBuildings(){ return Buildings; }

    public void setBuildings(List<BuildingInfo> buildings){ this.Buildings = buildings; }

}
