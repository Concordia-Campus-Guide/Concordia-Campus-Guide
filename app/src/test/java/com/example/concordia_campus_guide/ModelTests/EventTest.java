package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Event;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EventTest {
    Event event;

    @Before
    public void init() {
        event = new Event("This is a test", null);
    }

    @Test
    public void getSetIdTest() {
        int id = 1;
        event.setId(id);
        assertEquals(id, event.getId());
    }

    @Test
    public void getSetDescriptionTest() {
        String description = "test";
        event.setDescription(description);
        assertEquals(description, event.getDescription());
    }

    @Test
    public void getSetLocationTest() {
        RoomModel room = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
        event.setLocation(room);
        assertEquals(room, event.getLocation());
    }
}
