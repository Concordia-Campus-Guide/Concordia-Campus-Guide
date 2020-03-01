package com.example.concordia_campus_guide.Models;


import com.google.android.gms.maps.model.LatLng;

public class Building {

    private LatLng centerCoordinates;
    private String[] availableFloors;
    private String buildingCode;
    private float width;
    private float height;

    public Building(LatLng centerCoordinates, String[] availableFloors, String buildingCode, float width, float height) {
        this.centerCoordinates = centerCoordinates;
        this.availableFloors = availableFloors;
        this.buildingCode = buildingCode;
        this.width = width;
        this.height = height;
    }

    public LatLng getCenterCoordinates() {
        return centerCoordinates;
    }

    public void setCenterCoordinates(LatLng centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public String[] getAvailableFloors() {
        return availableFloors;
    }

    public void setAvailableFloors(String[] availableFloors) {
        this.availableFloors = availableFloors;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
