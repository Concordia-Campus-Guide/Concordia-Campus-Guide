package com.example.concordia_campus_guide.Models;

import android.text.TextUtils;

import com.google.android.gms.maps.model.GroundOverlayOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Building extends Place {
    //Attributes needed to create the ground overlays [ui]
    private String[] availableFloors;
    private Float width;
    private Float height;
    private Float bearing;
    private List<List<List<Double>>> coordinates;

    //Descriptive attributes
    private String campus;
    private String buildingCode;
    private String buildingName;
    private String address;
    private List<String> departments;
    private List<String> services;

    private GroundOverlayOptions groundOverlayOption;

    public Building(Double[] centerCoordinates, String[] availableFloors, float width, float height, float bearing,
                    String campus, String buildingCode, String buildingName, String address,
                    List<String> departments, List<String> services, List<List<List<Double>>> coordinates) {
        super(centerCoordinates);
        this.availableFloors = availableFloors;
        this.width = width;
        this.height = height;
        this.campus = campus;
        this.buildingCode = buildingCode;
        this.buildingName = buildingName;
        this.address = address;
        this.departments = departments;
        this.services = services;
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
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getServicesString(){
        if(services.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", services);
    }

    public String getDepartmentsString(){
        if(departments.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", departments);
    }

    public JSONObject getGeoJson(){
        JSONObject toReturn = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject geometry = new JSONObject();

        try{
            properties.put("code", buildingCode);
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
