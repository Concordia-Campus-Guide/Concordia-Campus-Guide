package com.example.concordia_campus_guide.Adapters;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.EncodedPolyline;
import com.example.concordia_campus_guide.Models.Direction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionWrapperTest {
    DirectionWrapper directionWrapper;
    Direction direction;
    EncodedPolyline polyline;


    @Before
    public void init(){
        directionWrapper = new DirectionWrapper();
        direction = new Direction();
        direction.setDescription("Go left");
        polyline = new EncodedPolyline("testPoints");
        directionWrapper.setDirection(direction);
        directionWrapper.setPolyline(polyline);
    }

    @Test
    public void getDirection() {
       assertEquals("Go left", directionWrapper.getDirection().getDescription());
    }

    @Test
    public void setDirection() {
        assertEquals("Go left", directionWrapper.getDirection().getDescription());
        Direction updatedDirection = new Direction();
        updatedDirection.setDescription("Go right");
        assertEquals("Go right", directionWrapper.getDirection().getDescription());
    }

    @Test
    public void getPolyline() {
        assertEquals("testPoints", directionWrapper.getPolyline().getEncodedPath());
    }

    @Test
    public void setPolyline() {
        assertEquals("testPoints", directionWrapper.getPolyline().getEncodedPath());
        String updatedPath = "newTestPoints";
        EncodedPolyline newEncodedPolyline = new EncodedPolyline(updatedPath);
        directionWrapper.setPolyline(newEncodedPolyline);
        assertEquals("newTestPoints", directionWrapper.getPolyline().getEncodedPath());
    }
}