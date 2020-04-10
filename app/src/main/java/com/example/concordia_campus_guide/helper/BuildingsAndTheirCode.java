package com.example.concordia_campus_guide.helper;

import com.example.concordia_campus_guide.models.Building;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class BuildingsAndTheirCode {
    private Map<String, Building> buildings = new HashMap<>();

    public Building getBuildingFromCode(String buildingCode) {
        return buildings.get(buildingCode);
    }

    public Map<String, Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Map<String, Building> buildings) {
        this.buildings = buildings;
    }

    public LatLng getZoomLocation(String location){
        return buildings.get(location).getCenterCoordinatesLatLng();
    }
}
