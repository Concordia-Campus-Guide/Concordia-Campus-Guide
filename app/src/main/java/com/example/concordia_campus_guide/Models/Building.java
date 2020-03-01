package com.example.concordia_campus_guide.Models;


import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class Building {

    private LatLng centerCoordinates;
    private String[] availableFloors;
    private String buildingCode;
    private float width;
    private float height;
    private float bearing;
    private BuildingInfo buildingInformation;

    private GroundOverlayOptions groundOverlayOption;

    public Building(LatLng centerCoordinates, String[] availableFloors, String buildingCode, float width, float height, float bearing) {
        this.centerCoordinates = centerCoordinates;
        this.availableFloors = availableFloors;
        this.buildingCode = buildingCode;
        this.width = width;
        this.height = height;
        this.groundOverlayOption = null;
        this.bearing = bearing;
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

    public GroundOverlayOptions getGroundOverlayOption() {
        return groundOverlayOption;
    }

    public void setGroundOverlayOption(GroundOverlayOptions groundOverlayOption) {
        this.groundOverlayOption = groundOverlayOption;
    }
    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
}
