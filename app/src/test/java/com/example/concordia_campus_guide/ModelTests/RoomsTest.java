package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Rooms;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RoomsTest {
    private List<RoomModel> roomList;
    private Rooms rooms;

    @Before
    public void init(){
        roomList = new ArrayList<>();
        RoomModel room1  = new RoomModel(new Double[]{-73.57907921075821, 45.49702057370776}, "823", "H-8");
        RoomModel room2  = new RoomModel(new Double[]{-73.57902321964502, 45.49699848270905}, "821", "H-8");

        roomList.add(room1);
        roomList.add(room2);
        rooms = new Rooms(roomList);
    }

    @Test
    public void getRoomsTest(){
        assertEquals(rooms.getRooms(), roomList);
    }
    @Test
    public void getPlacesTest(){
        List<Place> places = new ArrayList<Place>();
             for(RoomModel room: roomList){
                  places.add(room);
              }
        assertEquals(rooms.getPlaces(), places);
    }

}
