package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;
import com.example.concordia_campus_guide.Models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BuildingsTest {
    TestUtils testUtils = new TestUtils();
    @Before
    public void init() {
        testUtils = new TestUtils();
        testUtils.defaultBuildings = new Buildings();
        testUtils.listOfBuildings.add(testUtils.building1);
        testUtils.listOfBuildings.add(testUtils.building2);
        testUtils.defaultBuildings.setBuildings(testUtils.listOfBuildings);
        testUtils.buildingsWithList = new Buildings(testUtils.listOfBuildings);
    }

    @Test
    public void buildingsConstructorWithList(){
        assertNotNull(testUtils.buildingsWithList);
        assertEquals(testUtils.listOfBuildings, testUtils.buildingsWithList.getBuildings());
        assertEquals(testUtils.building1, testUtils.buildingsWithList.getBuilding(testUtils.building1.getBuildingCode()));
    }

    @Test
    public void getBuildings() {
        assertEquals(testUtils.listOfBuildings, testUtils.defaultBuildings.getBuildings());
    }

    @Test
    public void getPlaces() {
        List<Place> places = new ArrayList<Place>();
        places = testUtils.defaultBuildings.getPlaces();
        assertEquals(2, places.size());
        assertEquals(testUtils.building1, places.get(0));
        assertEquals(testUtils.building2, places.get(1));
    }

    @Test
    public void getBuilding() {
        assertEquals(testUtils.building1, testUtils.defaultBuildings.getBuilding("H"));
    }

    @Test
    public void getBuildingNull(){
        assertNull(testUtils.defaultBuildings.getBuilding("Z"));
    }

    @Test
    public void setBuildings() {
        List<Building> updateBuildings = new ArrayList<Building>();
        updateBuildings.add(testUtils.building1);
        assertEquals(2, testUtils.defaultBuildings.getBuildings().size());
        testUtils.defaultBuildings.setBuildings(updateBuildings);
        assertEquals(1, testUtils.defaultBuildings.getBuildings().size());
        assertEquals(testUtils.building1, testUtils.defaultBuildings.getBuildings().get(0));
    }

    @Test
    public void getGeoJson() {
        JSONObject toReturn = new JSONObject();
        testUtils.listOfBuildings = new ArrayList<>();
        testUtils.listOfBuildings.add(testUtils.building1);
        testUtils.defaultBuildings.setBuildings(testUtils.listOfBuildings);

        try {
            toReturn.put("type", "FeatureCollection");
            toReturn.put("features", getInnerGeoJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(toReturn.toString(), testUtils.defaultBuildings.getGeoJson().toString());
    }

    public JSONArray getInnerGeoJson() {
        JSONArray features = new JSONArray();
        try {
            JSONObject building1GeoJson = testUtils.building1.getGeoJson();
            if (building1GeoJson != null) features.put(building1GeoJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return features;
    }
}