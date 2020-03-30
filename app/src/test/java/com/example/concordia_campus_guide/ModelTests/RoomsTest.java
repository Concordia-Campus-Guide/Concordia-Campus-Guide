package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RoomsTest {
    TestUtils testUtils= new TestUtils();

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getRoomsTest() {
        assertEquals(testUtils.roomList, testUtils.rooms.getRooms());
    }

    @Test
    public void getPlacesTest() {
        List<Place> places = new ArrayList<Place>();
        for (RoomModel room : testUtils.roomList) {
            places.add(room);
        }
        assertEquals(places, testUtils.rooms.getPlaces());
    }

    @Test
    public void getRoomsByFloorTest() {
        List<RoomModel> roomListTest = new ArrayList<RoomModel>();
        roomListTest.add(testUtils.roomList.get(0));
        assertEquals(roomListTest, testUtils.rooms.getRoomsByFloor("H-8"));
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
        assertEquals(toReturn.toString(), testUtils.rooms.getGeoJson("H-8").toString());
    }

    public JSONArray getInnerGeoJsonTest() {
        JSONArray features = new JSONArray();
        RoomModel roomTest = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8", "SGW");
        try {
            JSONObject roomGeoJSON = roomTest.getGeoJson();
            if (roomGeoJSON != null) features.put(roomGeoJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return features;
    }

}
