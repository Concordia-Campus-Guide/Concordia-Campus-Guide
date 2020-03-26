package com.example.concordia_campus_guide.ModelTests;

import android.text.TextUtils;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BuildingTest {
    TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getAvailableFloors() {
        assertNull(testUtils.defaultBuilding.getAvailableFloors());
        assertEquals(testUtils.availableFloors,testUtils.buildingWithArgs.getAvailableFloors());
    }

    @Test
    public void setAvailableFloors() {
        ArrayList<String> updateAvailableFloors =  new ArrayList<String>(Arrays.asList("0", "1", "8", "9"));
        assertEquals(testUtils.availableFloors, testUtils.buildingWithArgs.getAvailableFloors());
        testUtils.buildingWithArgs.setAvailableFloors(updateAvailableFloors);
        assertNotEquals(testUtils.availableFloors, testUtils.buildingWithArgs.getAvailableFloors());
        assertEquals(updateAvailableFloors, testUtils.buildingWithArgs.getAvailableFloors());
    }

    @Test
    public void getWidth() {
        assertEquals(68, testUtils.buildingWithArgs.getWidth(), 0.001);
    }

    @Test
    public void setWidth() {
        assertEquals(68, testUtils.buildingWithArgs.getWidth(), 0.001);
        testUtils.buildingWithArgs.setWidth(10);
        assertEquals(10, testUtils.buildingWithArgs.getWidth(), 0.001);
    }

    @Test
    public void getHeight() {
        assertEquals(68, testUtils.buildingWithArgs.getHeight(), 0.001);
    }

    @Test
    public void setHeight() {
        assertEquals(68, testUtils.buildingWithArgs.getHeight(), 0.001);
        testUtils.buildingWithArgs.setHeight(10);
        assertEquals(10, testUtils.buildingWithArgs.getHeight(), 0.001);
    }

    @Test
    public void getGroundOverlayOption() {
    }

    @Test
    public void setGroundOverlayOption() {
    }

    @Test
    public void getBearing() {
        assertEquals(34, testUtils.buildingWithArgs.getBearing(), 0.001);
    }

    @Test
    public void setBearing() {
        assertEquals(34, testUtils.buildingWithArgs.getBearing(), 0.001);
        testUtils.buildingWithArgs.setBearing(10);
        assertEquals(10, testUtils.buildingWithArgs.getBearing(), 0.001);
    }

    @Test
    public void getBuildingCode() {
        assertEquals(testUtils.buildingCode, testUtils.buildingWithArgs.getBuildingCode());
    }

    @Test
    public void setBuildingCode() {
        assertEquals(testUtils.buildingCode, testUtils.buildingWithArgs.getBuildingCode());
        testUtils.buildingWithArgs.setBuildingCode("GN");
        assertEquals("GN", testUtils.buildingWithArgs.getBuildingCode());
    }

    @Test
    public void getBuilding_Long_Name() {
        assertEquals(testUtils.Building_Long_Name, testUtils.buildingWithArgs.getBuilding_Long_Name());
    }

    @Test
    public void setBuilding_Long_Name() {
        assertEquals(testUtils.Building_Long_Name, testUtils.buildingWithArgs.getBuilding_Long_Name());
        testUtils.buildingWithArgs.setBuilding_Long_Name("Grey Nuns Building");
        assertEquals("Grey Nuns Building", testUtils.buildingWithArgs.getBuilding_Long_Name());
    }

    @Test
    public void getAddress() {
        assertEquals(testUtils.address, testUtils.buildingWithArgs.getAddress());
    }

    @Test
    public void setAddress() {
        assertEquals(testUtils.address, testUtils.buildingWithArgs.getAddress());
        testUtils.buildingWithArgs.setAddress("1190 Guy Street");
        assertEquals("1190 Guy Street", testUtils.buildingWithArgs.getAddress());
    }

    @Test
    public void getDepartments() {
        assertEquals(testUtils.departments, testUtils.buildingWithArgs.getDepartments());
    }

    @Test
    public void setDepartments() {
        ArrayList<String> updateDepartments =  new ArrayList<String>(Arrays.asList("dep1", "dep2", "dep3", "dep4"));
        assertEquals(testUtils.departments, testUtils.buildingWithArgs.getDepartments());
        testUtils.buildingWithArgs.setDepartments(updateDepartments);
        assertEquals(updateDepartments, testUtils.buildingWithArgs.getDepartments());
    }

    @Test
    public void getServices() {
        assertEquals(testUtils.services, testUtils.buildingWithArgs.getServices());
    }

    @Test
    public void setServices() {
        ArrayList<String> updateServices =  new ArrayList<String>(Arrays.asList("serv1", "serv2", "serv3"));
        assertEquals(testUtils.services, testUtils.buildingWithArgs.getServices());
        testUtils.buildingWithArgs.setServices(updateServices);
        assertEquals(updateServices, testUtils.buildingWithArgs.getServices());
    }

    @Test
    public void getCornerCoordinates() {
        assertEquals(testUtils.cornerCoordinates, testUtils.buildingWithArgs.getCornerCoordinates());
    }

    @Test
    public void setCornerCoordinates() {
        List<Coordinates> updatedListOfCornerCoordinates = new ArrayList<Coordinates>() {{
            add(testUtils.cornerCoordinate1);
            add(testUtils.cornerCoordinate2);
        }};
        ListOfCoordinates updatedCornerCoordinates =  new ListOfCoordinates(updatedListOfCornerCoordinates);
        assertEquals(testUtils.cornerCoordinates, testUtils.buildingWithArgs.getCornerCoordinates());
        testUtils.buildingWithArgs.setCornerCoordinates(updatedCornerCoordinates);
        assertEquals(updatedCornerCoordinates, testUtils.buildingWithArgs.getCornerCoordinates());
    }

    @Test
    public void getServicesStringEmpty(){
        testUtils.buildingWithArgs.setServices(new ArrayList<>());
        assertTrue(testUtils.buildingWithArgs.getServices().isEmpty());
        assertEquals("", testUtils.buildingWithArgs.getServicesString());
    }

    @Test
    public void getServicesString() {
        assertFalse(testUtils.buildingWithArgs.getServices().isEmpty());
    }

    @Test
    public void getDepartmentsStringEmpty() {
        testUtils.buildingWithArgs.setDepartments(new ArrayList<>());
        assertTrue(testUtils.buildingWithArgs.getDepartments().isEmpty());
        assertEquals("", testUtils.buildingWithArgs.getDepartmentsString());
    }

    @Test
    public void getDepartmentsString() {
        assertFalse(testUtils.buildingWithArgs.getDepartments().isEmpty());
    }

    @Test
    public void getGeoJson() throws JSONException {
        assertNotNull(testUtils.buildingWithArgs.getGeoJson());
    }

    @Test
    public void getDisplayName() {
        assertEquals(testUtils.Building_Long_Name, testUtils.buildingWithArgs.getDisplayName());
    }
}