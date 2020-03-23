package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoomModelTest {

    private RoomModel room;

    @Before
    public void init() {
        room = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
    }

    @Test
    public void getAndSetFloorCodeTest() {
        String floorCode = room.getFloorCode();
        room.setFloorCode("H-8");
        assertEquals(room.getFloorCode(), "H-8");
        room.setFloorCode(floorCode);
    }
    @Test
    public void getAndSetRoomCodeTest() {
        String roomCode = room.getRoomCode();
        room.setRoomCode("822");
        assertEquals(room.getRoomCode(), "822");
        assertEquals(room.getDisplayName(), "H-8 822");
        room.setFloorCode(roomCode);
    }

    @Test
    public void getAndSetCenterCoordinatesTest() {
        Coordinates centerCoordinates = room.getCenterCoordinates();
        Coordinates centerCoordinatesNew = new Coordinates(-73.57921063899994, 45.49707133596979);
        room.setCenterCoordinates(centerCoordinatesNew);
        assertEquals(room.getCenterCoordinates(), centerCoordinatesNew);
        room.setCenterCoordinates(centerCoordinates);
    }

    @Test
    public void getAndSetIdTest() {
        long idOg = room.getId();
        room.setId(800);
        assertEquals(room.getId(), 800);
        room.setId(idOg);
    }

    @Test
    public void getCenterCoordinatesLatLngTest() {
        Coordinates coordinates = room.getCenterCoordinates();
        assertEquals(room.getCenterCoordinatesLatLng(), new LatLng(coordinates.getLatitude(), coordinates.getLongitude()));
    }
}
