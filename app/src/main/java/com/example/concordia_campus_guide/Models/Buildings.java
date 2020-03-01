package com.example.concordia_campus_guide.Models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Buildings {

    private List<Building> Buildings;

    public Buildings(){
        Buildings = new ArrayList<Building>();
    }

    public List<Building> getBuildings(){
        return Buildings;
    }

    public Building getBuilding(String buildingCode){
        for(Building building: Buildings){
            if(building.getBuildingCode().equals(buildingCode)){
                return building;
            }
        }
        return null;
    }

    public Buildings(List<Building> buildings){
        this.Buildings = buildings;
    }

    public void setBuildings(List<Building> buildings){
        this.Buildings = buildings;
    }

    public JsonObject toGeoJson(){
        return null;
    }

}
