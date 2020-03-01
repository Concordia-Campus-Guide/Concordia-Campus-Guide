package com.example.concordia_campus_guide.Models;


import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Building {

    private Double[] centerCoordinates;
    private String[] availableFloors;
    private Float width;
    private Float height;
    private Float bearing;
    private String Campus;
    private String BuildingCode;
    private String Building_Long_Name;
    private String Address;
    private List<String> Departments;
    private List<String> Services;
    private List<List<List<Double>>> coordinates;

    public Building(Double[] centerCoordinates, String[] availableFloors, float width, float height, String campus, String buildingCode, String building_Long_Name, String address, List<String> departments, List<String> services, List<List<List<Double>>> coordinates) {
        this.centerCoordinates = centerCoordinates;
        this.availableFloors = availableFloors;
        this.width = width;
        this.height = height;
        Campus = campus;
        BuildingCode = buildingCode;
        Building_Long_Name = building_Long_Name;
        Address = address;
        Departments = departments;
        Services = services;
        this.coordinates = coordinates;
    }

    public Building(LatLng centerPos, String[] floorsAvailable, String building_code, float building_width, float building_height) {
        centerCoordinates = new Double[]{ (double)centerPos.latitude, (double)centerPos.longitude };
        availableFloors = floorsAvailable;
        BuildingCode = building_code;
        width = building_width;
        height = building_height;
    }

    public LatLng getCenterCoordinates() {
        return new LatLng(centerCoordinates[0], centerCoordinates[1]);
    }

    public void setCenterCoordinates(LatLng centerCoordinates) {
        this.centerCoordinates = new Double[]{ (double)centerCoordinates.latitude, (double)centerCoordinates.longitude };
    }

    public String[] getAvailableFloors() {
        return availableFloors;
    }

    public void setAvailableFloors(String[] availableFloors) {
        this.availableFloors = availableFloors;
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

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getBuildingCode() {
        return BuildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        BuildingCode = buildingCode;
    }

    public String getBuilding_Long_Name() {
        return Building_Long_Name;
    }

    public void setBuilding_Long_Name(String building_Long_Name) {
        Building_Long_Name = building_Long_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<String> getDepartments() {
        return Departments;
    }

    public void setDepartments(List<String> departments) {
        Departments = departments;
    }

    public List<String> getServices() {
        return Services;
    }

    public void setServices(List<String> services) {
        Services = services;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getServicesString(){
        if(Services.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", Services);
    }

    public String getDepartmentsString(){
        if(Departments.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", Departments);
    }

    public JSONObject getGeoJson(){
        JSONObject toReturn = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject geometry = new JSONObject();

        try{
            properties.put("code", BuildingCode);
            if(centerCoordinates!=null) properties.put("center", ""+centerCoordinates[0]+", "+centerCoordinates[1]);
            if(height!=null) properties.put("height", height);
            if(width!=null) properties.put("width", width);
            if(bearing!=null) properties.put("bearing", bearing);
            if(availableFloors!=null) properties.put("floorsAvailable", TextUtils.join(", ", availableFloors));

            if(coordinates==null) return null;

            geometry.put("type", "Polygon");
            geometry.put("coordinates", new JSONArray(coordinates));

            toReturn.put("type", "Feature");
            toReturn.put("properties", properties);
            toReturn.put("geometry", geometry);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return toReturn;
    }
}
