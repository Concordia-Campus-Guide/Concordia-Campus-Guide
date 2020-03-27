package com.example.concordia_campus_guide.ModelTests;

import androidx.room.Room;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public TestUtils(){}

    // Buildings
    public Buildings defaultBuildings;
    Buildings buildingsWithList;

    public Coordinates centerCoordinates = new Coordinates(45.4972685,-73.5789475);
    public List<String> availableFloors=new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
    public float width = 68;
    public float height = 68;
    public float bearing = 34;
    public String SGWcampus = "SGW";
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

    List<Coordinates> listOfCornerCoordinates = new ArrayList<Coordinates>(Arrays.asList(cornerCoordinate1, cornerCoordinate2, cornerCoordinate3, cornerCoordinate4,cornerCoordinate5));

    public ListOfCoordinates cornerCoordinates = new ListOfCoordinates(listOfCornerCoordinates);

    Building building1 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, SGWcampus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );
    Building building2 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, SGWcampus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );

    List<Building> listOfBuildings = new ArrayList<Building>(Arrays.asList(building1, building2));

    public Building defaultBuilding = new Building();
    public Building buildingWithArgs = new Building(centerCoordinates, availableFloors, width, height,
                                             bearing, SGWcampus, buildingCode, Building_Long_Name,  address, departments,
                                             services,  cornerCoordinates);

    // Floors
    Coordinates coordinates = new Coordinates(45.4972685, -73.5789475);
    String floorCode = "H-9";
    float altitude = 0;

    Floor defaultFloor= new Floor();
    Floor floorWithArgs= new Floor(coordinates, floorCode, altitude);;
    Floor floorWithCampus=new Floor(coordinates, floorCode, altitude, SGWcampus);

    List<Floor> listOfFloors = new ArrayList<Floor>(Arrays.asList(floorWithArgs, floorWithCampus));

    Floors floors = new Floors();

    // Rooms
    RoomModel room1 = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
    RoomModel room2 = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9");

    ArrayList<RoomModel> roomList = new ArrayList<RoomModel>(Arrays.asList(room1, room2));
    public Rooms rooms = new Rooms(roomList);



}
