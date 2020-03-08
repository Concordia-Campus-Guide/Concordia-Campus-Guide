package com.example.concordia_campus_guide.Models;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.Database.Converters.StringListConverter;
import com.google.android.gms.maps.model.GroundOverlayOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Entity (tableName = "buildings",
        primaryKeys = {"building_code"}
        )
public class Building extends Place {
    /**
     * These following attributes NEED to match the fields in the JSON file. Do not change them.
     */

    //Needs to be modified to list of floors instead.
    @ColumnInfo(name = "availableFloors")
    @TypeConverters(StringListConverter.class)
    private List<String> availableFloors;

    @ColumnInfo(name = "width")
    private float width;

    @ColumnInfo(name = "height")
    private float height;

    @ColumnInfo(name = "bearing")
    private float bearing;

    //Not sure what to do here yet
    //
    //
    @Ignore
    private List<List<List<Double>>> coordinates;

    //Descriptive Attributes
    @ColumnInfo(name = "campus")
    private String Campus;

    @ColumnInfo(name = "building_code")
    @NonNull
    private String BuildingCode;

    @ColumnInfo(name = "building_long_name")
    private String Building_Long_Name;

    @ColumnInfo(name = "address")
    private String Address;

    @ColumnInfo(name = "departments")
    @TypeConverters(StringListConverter.class)
    private List<String> Departments;

    @ColumnInfo(name = "services")
    @TypeConverters(StringListConverter.class)
    private List<String> Services;

    @Ignore
    private GroundOverlayOptions groundOverlayOption;

    public Building(){}
    public Building(Double[] centerCoordinates, List<String> availableFloors, float width, float height, float bearing,
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

    public List<String> getAvailableFloors() {
        return availableFloors;
    }

    public void setAvailableFloors(List<String> availableFloors) {
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
            if(height!=0) properties.put("height", height);
            if(width!=0) properties.put("width", width);
            if(bearing!=0) properties.put("bearing", bearing);
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
