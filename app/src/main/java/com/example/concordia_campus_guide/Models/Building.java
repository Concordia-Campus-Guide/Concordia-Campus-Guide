package com.example.concordia_campus_guide.Models;

import android.text.TextUtils;

import com.google.android.gms.maps.model.GroundOverlayOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Building extends Place {
    /**
     * These following attributes NEED to match the fields in the JSON file. Do not change them.
     */
    //Attributes needed to create the ground overlays [ui]
    private String[] availableFloors;
    private Float width;
    private Float height;
    private Float bearing;
    private List<List<List<Double>>> coordinates;

    //Descriptive Attributes
    private String Campus;
    private String BuildingCode;
    private String Building_Long_Name;
    private String Address;
    private List<String> Departments;
    private List<String> Services;

    private GroundOverlayOptions groundOverlayOption;

    public Building(Double[] centerCoordinates, String[] availableFloors, float width, float height, float bearing,
                    String campus, String buildingCode, String Building_Long_Name, String address,
                    List<String> departments, List<String> services, List<List<List<Double>>> coordinates) {
        super(centerCoordinates);
        this.availableFloors = availableFloors;
        this.width = width;
        this.height = height;
        this.Campus = campus;
        this.BuildingCode = buildingCode;
        this.Building_Long_Name = Building_Long_Name;
        this.Address = address;
        this.Departments = departments;
        this.Services = services;
        this.bearing = bearing;
        this.coordinates = coordinates;
        this.groundOverlayOption = null;
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

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        this.Campus = campus;
    }

    public String getBuildingCode() {
        return BuildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.BuildingCode = buildingCode;
    }

    public String getBuilding_Long_Name() {
        return Building_Long_Name;
    }

    public void setBuilding_Long_Name(String building_Long_Name) {
        this.Building_Long_Name = building_Long_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public List<String> getDepartments() {
        return Departments;
    }

    public void setDepartments(List<String> departments) {
        this.Departments = departments;
    }

    public List<String> getServices() {
        return Services;
    }

    public void setServices(List<String> services) {
        this.Services = services;
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
            if(availableFloors!=null) properties.put("floorsAvailable", TextUtils.join(",", availableFloors));

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
