package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.models.Coordinates;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoomModelTest {
    TestUtils testUtils = new TestUtils();

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getAndSetFloorCodeTest() {
        String floorCode = testUtils.room1.getFloorCode();
        testUtils.room1.setFloorCode("H-8");
        assertEquals(testUtils.room1.getFloorCode(), "H-8");
        testUtils.room1.setFloorCode(floorCode);
    }

    @Test
    public void getAndSetRoomCodeTest() {
        String roomCode = testUtils.room1.getRoomCode();
        testUtils.room1.setRoomCode("822");
        assertEquals(testUtils.room1.getRoomCode(), "822");
        assertEquals(testUtils.room1.getDisplayName(), "H-8 822");
        testUtils.room1.setFloorCode(roomCode);
    }

    @Test
    public void getAndSetCenterCoordinatesTest() {
        Coordinates centerCoordinates = testUtils.room1.getCenterCoordinates();
        Coordinates centerCoordinatesNew = new Coordinates(-73.57921063899994, 45.49707133596979);
        testUtils.room1.setCenterCoordinates(centerCoordinatesNew);
        assertEquals(testUtils.room1.getCenterCoordinates(), centerCoordinatesNew);
        testUtils.room1.setCenterCoordinates(centerCoordinates);
    }

    @Test
    public void getAndSetIdTest() {
        long idOg = testUtils.room1.getId();
        testUtils.room1.setId(800);
        assertEquals(testUtils.room1.getId(), 800);
        testUtils.room1.setId(idOg);
    }

    @Test
    public void getCenterCoordinatesLatLngTest() {
        Coordinates coordinates = testUtils.room1.getCenterCoordinates();
        assertEquals(testUtils.room1.getCenterCoordinatesLatLng(), new LatLng(coordinates.getLatitude(), coordinates.getLongitude()));
    }

    @Test
    public void getDisplayNameTest() {
        String expected = "H-8 823";
        assertEquals(expected, testUtils.room1.getDisplayName());
    }

    @Test
    public void getGeoJsonTest() {
        JSONObject toReturn = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject geometry = new JSONObject();

        try {
            toReturn.put("type", "Feature");

            properties.put("floorCode", "H-8");
            properties.put("roomCode", "823");
            toReturn.put("properties", properties);

            geometry.put("type", "Point");
            Double[] geoJsonCoordinates = { 45.49702057370776, -73.57907921075821 };
            geometry.put("coordinates", new JSONArray(geoJsonCoordinates));
            toReturn.put("geometry", geometry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(toReturn.toString(), testUtils.room1.getGeoJson().toString());
    }
}
