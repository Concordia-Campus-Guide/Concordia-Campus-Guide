package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Buildings;
import com.example.concordia_campus_guide.models.Place;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BuildingsTest {
     static TestUtils testUtils;

    @BeforeClass
    public static void instantiate(){
        testUtils = new TestUtils();
    }

    @Before
    public void init() {
        testUtils.defaultBuildings = new Buildings();
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
        assertEquals(testUtils.listOfBuildings, testUtils.buildingsWithList.getBuildings());
    }

    @Test
    public void getPlaces() {
        List<Place> places = new ArrayList<Place>();
        places = testUtils.buildingsWithList.getPlaces();
        assertEquals(2, places.size());
        assertEquals(testUtils.building1, places.get(0));
        assertEquals(testUtils.building2, places.get(1));
    }

    @Test
    public void getBuilding() {
        assertEquals(testUtils.building1, testUtils.buildingsWithList.getBuilding("H"));
    }

    @Test
    public void getBuildingNull(){
        assertNull(testUtils.defaultBuildings.getBuilding("Z"));
    }

    @Test
    public void setBuildings() {
        List<Building> updateBuildings = new ArrayList<Building>();
        updateBuildings.add(testUtils.building1);
        assertEquals(2, testUtils.buildingsWithList.getBuildings().size());
        testUtils.buildingsWithList.setBuildings(updateBuildings);
        assertEquals(1, testUtils.buildingsWithList.getBuildings().size());
        assertEquals(testUtils.building1, testUtils.buildingsWithList.getBuildings().get(0));
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