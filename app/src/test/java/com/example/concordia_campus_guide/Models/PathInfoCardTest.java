package com.example.concordia_campus_guide.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathInfoCardTest {
    private PathInfoCard pathInfoCard;

    @Before
    public void setUp() throws Exception {
        pathInfoCard = new PathInfoCard("WALKING", 10.0, "Test");
    }

    @Test
    public void getDuration() {
        double duration = pathInfoCard.getDuration();
        assertEquals(10.0, duration, 0);
    }

    @Test
    public void getDescription() {
        String description = pathInfoCard.getDescription();
        assertEquals("Test", description);
    }

    @Test
    public void getType() {
        String type = pathInfoCard.getType();
        assertEquals("WALKING", type);
    }
}