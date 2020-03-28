package com.example.concordia_campus_guide.ModelTests.TestUtils;

import androidx.room.Room;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.LatLng;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.Distance;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Models.ListOfCoordinates;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Rooms;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.google.android.gms.maps.model.LatLng;
import com.example.concordia_campus_guide.Models.Shuttle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public TestUtils(){}

    // Buildings
    public Buildings defaultBuildings;
    public Buildings buildingsWithList;

    public Coordinates centerCoordinates = new Coordinates(45.4972685,-73.5789475);
    public List<String> availableFloors = new ArrayList<String>(Arrays.asList("00", "1", "8", "9"));
    public float width = 68;
    public float height = 68;
    public float bearing = 34;
    public String SGWcampus = "SGW";
    public String LOYCampus = "LOY";
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

    public Coordinates cornerCoordinate1 = new Coordinates(45.497178,-73.57955);
    public Coordinates cornerCoordinate2 = new Coordinates(45.497708,-73.579035);
    public Coordinates cornerCoordinate3 = new Coordinates(45.497385,-73.578332);
    public Coordinates cornerCoordinate4 = new Coordinates(45.496832,-73.578842);
    public Coordinates cornerCoordinate5 = new Coordinates(45.497178,-73.57955);

    public List<Coordinates> listOfCornerCoordinates = new ArrayList<Coordinates>(Arrays.asList(cornerCoordinate1, cornerCoordinate2, cornerCoordinate3, cornerCoordinate4,cornerCoordinate5));

    public ListOfCoordinates cornerCoordinates = new ListOfCoordinates(listOfCornerCoordinates);

    public Building building1 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, SGWcampus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );
    public Building building2 = new Building(centerCoordinates, availableFloors, width, height,
            bearing, LOYCampus, buildingCode, Building_Long_Name,  address, departments,
            services,  cornerCoordinates
    );

    public List<Building> listOfBuildings = new ArrayList<Building>(Arrays.asList(building1, building2));

    public Building defaultBuilding = new Building();
    public Building buildingWithArgs = new Building(centerCoordinates, availableFloors, width, height,
                                             bearing, SGWcampus, buildingCode, Building_Long_Name,  address, departments,
                                             services,  cornerCoordinates);

    // Floors
    public Coordinates coordinates = new Coordinates(45.4972685, -73.5789475);
    public String floorCode = "H-9";
    public float altitude = 0;

    public Floor defaultFloor= new Floor();
    public Floor floorWithArgs= new Floor(coordinates, floorCode, altitude);;
    public Floor floorWithCampus=new Floor(coordinates, floorCode, altitude, SGWcampus);

    public List<Floor> listOfFloors = new ArrayList<Floor>(Arrays.asList(floorWithArgs, floorWithCampus));

    public Floors floors = new Floors();

    // Rooms
    public RoomModel room1 = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
    public RoomModel room2 = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9");

    public ArrayList<RoomModel> roomList = new ArrayList<RoomModel>(Arrays.asList(room1, room2));
    public Rooms rooms = new Rooms(roomList);


    public Shuttle getShuttle1(){
        Shuttle shuttle1 = new Shuttle();
        shuttle1.setCampus("SGW");
        shuttle1.setDay(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday","Friday"));
        shuttle1.setShuttleId(1);
        shuttle1.setTime(8.20);
        return shuttle1;
    }

    public Shuttle getShuttle2(){
        Shuttle shuttle2 = new Shuttle();
        shuttle2.setCampus("LOY");
        shuttle2.setDay(Arrays.asList("Monday", "friday"));
        shuttle2.setShuttleId(2);
        shuttle2.setTime(9.10);
        return  shuttle2;
    }

    public static DirectionWrapper getDirectionWrapper1(){
        DirectionWrapper directionWrapper1 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(60.0);
        direction1.setDescription("Go Left");

        directionWrapper1.setDirection(direction1);
        return directionWrapper1;
    }

    public static DirectionWrapper getDirectionWrapper2(){
        DirectionWrapper directionWrapper2 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(120.0);
        direction1.setDescription("Go Right");

        directionWrapper2.setDirection(direction1);
        return directionWrapper2;
    }

    public static List<DirectionWrapper> getListOfDirectionWrappers(){
        return new ArrayList<>(Arrays.asList(getDirectionWrapper1(), getDirectionWrapper2()));
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng1(){
        // room 819
        return new com.google.android.gms.maps.model.LatLng(45.496951715566176, -73.5789605230093);
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng2(){
        // room 821
        return new com.google.android.gms.maps.model.LatLng(45.49699848270905,-73.57902321964502);
    }

    public static String getDirectionDescription(){
        return "Go Left";
    }




}
