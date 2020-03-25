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
    Buildings defaultBuildings;
    Buildings buildingsWithList;
    List<Building> listOfBuildings = new ArrayList<>();
    private Coordinates centerCoordinates = new Coordinates(45.4972685,-73.5789475);
    private List<String> availableFloors=new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
    private float width = 68;
    private float height = 68;
    private float bearing = 34;
    private String campus = "SGW";
    private String buildingCode = "H";
    private String Building_Long_Name = "Henry F. Hall Building";
    private String address = "1455 De Maisonneuve West";
    private List<String> departments = new ArrayList<String>(Arrays.asList(
            "Geography, Planning and Environment",
            "Political Science, Socialogy and Anthropology, Economics",
            "School of Irish Studies"
    ));
    private List<String> services = new ArrayList<String>(Arrays.asList("Welcome Crew Office",
            "DB Clarke Theatre",
            "Dean of Students",
            "Aboriginal Student Resource Centre",
            "Concordia Student Union",
            "IT Service Desk",
            "Security Office",
            "Student Success Centre",
            "Mail Services",
            "Archives",
            "Career and Planning Services",
            "Sexual Assault Resource Centre (SARC)"
    ));

    Coordinates cornerCoordinate1 = new Coordinates(45.497178,-73.57955);
    Coordinates cornerCoordinate2 = new Coordinates(45.497708,-73.579035);
    Coordinates cornerCoordinate3 = new Coordinates(45.497385,-73.578332);
    Coordinates cornerCoordinate4 = new Coordinates(45.496832,-73.578842);
    Coordinates cornerCoordinate5 = new Coordinates(45.497178,-73.57955);

    List<Coordinates> listOfCornerCoordinates = new ArrayList<Coordinates>() {{
        add(cornerCoordinate1);
        add(cornerCoordinate2);
        add(cornerCoordinate3);
        add(cornerCoordinate4);
        add(cornerCoordinate5);
    }};

    private ListOfCoordinates cornerCoordinates = new ListOfCoordinates(listOfCornerCoordinates);

    Building building1 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, campus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );
    Building building2 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, campus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );

    @Before
    public void init() {
        defaultBuildings = new Buildings();
        listOfBuildings.add(building1);
        listOfBuildings.add(building2);
        defaultBuildings.setBuildings(listOfBuildings);
        buildingsWithList = new Buildings(listOfBuildings);
    }

    @Test
    public void buildingsConstructorWithList(){
        assertNotNull(buildingsWithList);
        assertEquals(listOfBuildings, buildingsWithList.getBuildings());
        assertEquals(building1, buildingsWithList.getBuilding(building1.getBuildingCode()));
    }

    @Test
    public void getBuildings() {
        assertEquals(listOfBuildings, defaultBuildings.getBuildings());
    }

    @Test
    public void getPlaces() {
        List<Place> places = new ArrayList<Place>();
        places = defaultBuildings.getPlaces();
        assertEquals(2, places.size());
        assertEquals(building1, places.get(0));
        assertEquals(building2, places.get(1));
//        TODO: is it supposed to be a place? it returns a Building
//        assertEquals(Place.class, places.get(0).getClass());
    }

    @Test
    public void getBuilding() {
        assertEquals(building1, defaultBuildings.getBuilding("H"));
    }

    @Test
    public void getBuildingNull(){
        assertNull(defaultBuildings.getBuilding("Z"));
    }

    @Test
    public void setBuildings() {
        List<Building> updateBuildings = new ArrayList<Building>();
        updateBuildings.add(building1);
        assertEquals(2, defaultBuildings.getBuildings().size());
        defaultBuildings.setBuildings(updateBuildings);
        assertEquals(1, defaultBuildings.getBuildings().size());
        assertEquals(building1, defaultBuildings.getBuildings().get(0));
    }

    @Test
    public void getGeoJson() {
        JSONObject toReturn = new JSONObject();
        listOfBuildings = new ArrayList<>();
        listOfBuildings.add(building1);
        defaultBuildings.setBuildings(listOfBuildings);

        try {
            toReturn.put("type", "FeatureCollection");
            toReturn.put("features", getInnerGeoJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(toReturn.toString(), defaultBuildings.getGeoJson().toString());
    }

    public JSONArray getInnerGeoJson() {
        JSONArray features = new JSONArray();
        try {
            JSONObject building1GeoJson = building1.getGeoJson();
            if (building1GeoJson != null) features.put(building1GeoJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return features;
    }
}