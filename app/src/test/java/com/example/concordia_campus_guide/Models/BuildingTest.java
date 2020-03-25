package com.example.concordia_campus_guide.Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BuildingTest {
    Building defaultBuilding;
    Building buildingWithArgs;
    Coordinates centerCoordinates;
    List<String> availableFloors=new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
    float width;
    float height;
    float bearing;
    String campus;
    String buildingCode;
    String Building_Long_Name;
    String address;
    List<String> departments;
    List<String> services;
    ListOfCoordinates cornerCoordinates;


    @Before
    public void init() {
        defaultBuilding = new Building();
        buildingWithArgs = new Building(centerCoordinates, availableFloors, width, height,
                bearing, campus, buildingCode, Building_Long_Name,  address, departments,
                services,  cornerCoordinates
        );
    }

    @Test
    public void getAvailableFloors() {
//        ArrayList<String> availableFloors =  new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
        assertNull(defaultBuilding.getAvailableFloors());
        assertEquals(availableFloors,buildingWithArgs.getAvailableFloors());
    }

    @Test
    public void setAvailableFloors() {
    }

    @Test
    public void getWidth() {
    }

    @Test
    public void setWidth() {
    }

    @Test
    public void getHeight() {
    }

    @Test
    public void setHeight() {
    }

    @Test
    public void getGroundOverlayOption() {
    }

    @Test
    public void setGroundOverlayOption() {
    }

    @Test
    public void getBearing() {
    }

    @Test
    public void setBearing() {
    }

    @Test
    public void getBuildingCode() {
    }

    @Test
    public void setBuildingCode() {
    }

    @Test
    public void getBuilding_Long_Name() {
    }

    @Test
    public void setBuilding_Long_Name() {
    }

    @Test
    public void getAddress() {
    }

    @Test
    public void setAddress() {
    }

    @Test
    public void getDepartments() {
    }

    @Test
    public void setDepartments() {
    }

    @Test
    public void getServices() {
    }

    @Test
    public void setServices() {
    }

    @Test
    public void getCornerCoordinates() {
    }

    @Test
    public void setCornerCoordinates() {
    }

    @Test
    public void getServicesString() {
    }

    @Test
    public void getDepartmentsString() {
    }

    @Test
    public void getGeoJson() {
    }

    @Test
    public void getDisplayName() {
    }
}