package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public TestUtils(){}

    // Buildings
    public Buildings defaultBuildings;
    Buildings buildingsWithList;
    List<Building> listOfBuildings = new ArrayList<Building>(){{
        add(building1);
        add(building2);
    }};

    public Coordinates centerCoordinates = new Coordinates(45.4972685,-73.5789475);
    public List<String> availableFloors=new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
    public float width = 68;
    public float height = 68;
    public float bearing = 34;
    public String campus = "SGW";
    public String buildingCode = "H";
    public String Building_Long_Name = "Henry F. Hall Building";
    public String address = "1455 De Maisonneuve West";
    public List<String> departments = new ArrayList<String>(Arrays.asList(
            "Geography, Planning and Environment",
            "Political Science, Socialogy and Anthropology, Economics",
            "School of Irish Studies"
    ));
    public List<String> services = new ArrayList<String>(Arrays.asList("Welcome Crew Office",
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

    public ListOfCoordinates cornerCoordinates = new ListOfCoordinates(listOfCornerCoordinates);

    Building building1 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, campus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );
    Building building2 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, campus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );

    public Building defaultBuilding = new Building();
    public Building buildingWithArgs = new Building(centerCoordinates, availableFloors, width, height,
                                             bearing, campus, buildingCode, Building_Long_Name,  address, departments,
                                             services,  cornerCoordinates);


    // Floors

    // Rooms
}
