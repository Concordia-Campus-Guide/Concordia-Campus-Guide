package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Rooms;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RoomsTest {
    private List<RoomModel> roomList;
    private Rooms rooms;

    @Before
    public void init() {
        roomList = new ArrayList<>();
        RoomModel room1 = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
        RoomModel room2 = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9");

        roomList.add(room1);
        roomList.add(room2);
        rooms = new Rooms(roomList);
    }

    @Test
    public void getRoomsTest() {
        assertEquals(roomList, rooms.getRooms());
    }

    @Test
    public void getPlacesTest() {
        List<Place> places = new ArrayList<Place>();
        for (RoomModel room : roomList) {
            places.add(room);
        }
        assertEquals(places, rooms.getPlaces());
    }

    @Test
    public void getRoomsByFloorTest() {
        List<RoomModel> roomListTest = new ArrayList<RoomModel>();
        roomListTest.add(roomList.get(0));
        assertEquals(roomListTest, rooms.getRoomsByFloor("H-8"));
    }

    @Test
    public void getGeoJsonTest() {
        JSONObject toReturn = new JSONObject();
        try {
            toReturn.put("type", "FeatureCollection");
            toReturn.put("features", getInnerGeoJsonTest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(toReturn.toString(), rooms.getGeoJson("H-8").toString());
    }

    public JSONArray getInnerGeoJsonTest() {
        JSONArray features = new JSONArray();
        RoomModel roomTest = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
        try {
            JSONObject roomGeoJSON = roomTest.getGeoJson();
            if (roomGeoJSON != null) features.put(roomGeoJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return features;
    }

}
