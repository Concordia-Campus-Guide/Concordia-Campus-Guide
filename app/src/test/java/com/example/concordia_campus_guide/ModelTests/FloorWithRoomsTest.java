package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Relations.FloorWithRooms;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class FloorWithRoomsTest {
    private FloorWithRooms floorWithRooms;

    @Before
    public void init() {
        floorWithRooms = new FloorWithRooms();
    }

    @Test
    public void getAndSetFloor() {
        Floor floor = new Floor(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", 45, "SGW");
        floorWithRooms.setFloor(floor);
        assertEquals(floor,floorWithRooms.getFloor());
    }

    @Test
    public  void getAndSetRooms(){
        List <RoomModel> roomList = new ArrayList<>();
        RoomModel room1  = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
        RoomModel room2  = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "821", "H-8");
        roomList.add(room1);
        roomList.add(room2);
        floorWithRooms.setRooms(roomList);
        assertEquals(roomList.get(0),floorWithRooms.getRooms().get(0));
    }
}
