package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PolygonObject {
    public long getId() {
        return buildingId;
    }
    public void setId(long id) {
        this.buildingId = id;
    }
    @SerializedName("count")
    private long buildingId;
    private String buildingName;
    private String buildingLabel;
    private List<Double> buildingCoordinates;
    private List<List<Double>> coordinates;

    public PolygonObject() {
    }

    public PolygonObject(long id, String buildingName, String buildingLabel, List<Double> buildingCoordinates, List<List<Double>> coordinates) {
        this.buildingId = id;
        this.buildingName = buildingName;
        this.buildingLabel = buildingLabel;
        this.buildingCoordinates = buildingCoordinates;
        this.coordinates = coordinates;
    }



    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setBuildingLabel(String buildingLabel) {
        this.buildingLabel = buildingLabel;
    }

    public void setBuildingCoordinates(List<Double> buildingCoordinates) {
        this.buildingCoordinates = buildingCoordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingLabel() {
        return buildingLabel;
    }

    public List<Double> getBuildingCoordinates() {
        return buildingCoordinates;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
}
