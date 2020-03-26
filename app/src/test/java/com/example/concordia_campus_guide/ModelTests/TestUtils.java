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

    // Floors

    // Rooms
}
