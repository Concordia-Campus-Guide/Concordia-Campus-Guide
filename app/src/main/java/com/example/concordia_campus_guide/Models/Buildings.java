package com.example.concordia_campus_guide.Models;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public List<Place> getPlaces(){
        List<Place> toReturn = new ArrayList<Place>();
        for(Building building: Buildings){
            toReturn.add(building);
        }
        return toReturn;
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

    public JSONObject getGeoJson(){
        JSONObject toReturn = new JSONObject();
        try{
            toReturn.put("type", "FeatureCollection");
            toReturn.put("features", getInnerGeoJson());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return toReturn;
    }

    public JSONArray getInnerGeoJson(){
        JSONArray features = new JSONArray();

        try{
            for(Building building: Buildings){
                JSONObject buildingGeoJson = building.getGeoJson();
                if(buildingGeoJson!=null) features.put(building.getGeoJson());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return features;
    }

}
