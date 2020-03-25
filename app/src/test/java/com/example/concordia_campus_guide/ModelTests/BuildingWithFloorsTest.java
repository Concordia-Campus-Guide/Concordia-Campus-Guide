package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Relations.BuildingWithFloors;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BuildingWithFloorsTest {
    private BuildingWithFloors buildingWithFloors;

    @Before
    public void init() {
        buildingWithFloors = new BuildingWithFloors();
    }

    @Test
    public void getAndSetFloor() {
        Floor floor1 = new Floor(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", 45, "SGW");
        Floor floor2 = new Floor(new Coordinates(-73.57907921075233, 45.49702012317092), "H-8", 45, "SGW");
        List<Floor> listOfFloors = new ArrayList<>();
        listOfFloors.add(floor2);
        listOfFloors.add(floor1);
        buildingWithFloors.setFloors(listOfFloors);
        assertEquals(listOfFloors.get(0),buildingWithFloors.getFloors().get(0));
    }

    @Test
    public  void getAndSetBuildings(){
        Building building =new Building(new Coordinates(45.495638, -73.578258), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null);
        buildingWithFloors.setBuilding(building);
        assertEquals(building,buildingWithFloors.getBuilding());
    }
}