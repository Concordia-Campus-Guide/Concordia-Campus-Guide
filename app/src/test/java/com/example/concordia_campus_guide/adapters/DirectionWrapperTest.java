package com.example.concordia_campus_guide.adapters;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.EncodedPolyline;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.LatLng;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.models.Direction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DirectionWrapperTest {
    DirectionWrapper directionWrapper;
    Direction direction;
    EncodedPolyline polyline;
    DirectionsStep directionsStep;
    TransitDetails transitDetails;


    @Before
    public void init(){
        directionWrapper = new DirectionWrapper();
        direction = new Direction();
        direction.setDescription("Go left");
        polyline = new EncodedPolyline("testPoints");
        directionWrapper.setDirection(direction);
        directionWrapper.setPolyline(polyline);

        directionsStep = new DirectionsStep();
        directionsStep.startLocation = new LatLng(1,2);
        directionsStep.endLocation = new LatLng(3,4);
        directionsStep.travelMode = TravelMode.TRANSIT;
        directionsStep.htmlInstructions = "test";
        directionsStep.duration = new Duration();
        directionsStep.polyline = polyline;

        transitDetails = new TransitDetails();
        transitDetails.numStops = 7;
        directionsStep.transitDetails = transitDetails;
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
        directionWrapper.setDirection(updatedDirection);
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

    @Test
    public void populateAttributesFromStep() {
        DirectionWrapper directionWrapper = new DirectionWrapper();
        directionWrapper.populateAttributesFromStep(directionsStep);
        assertEquals(directionWrapper.getDirection().getDescription(), directionsStep.htmlInstructions);
    }

    @Test
    public void getTransitDetails(){
        DirectionWrapper directionWrapper = new DirectionWrapper();
        directionWrapper.populateAttributesFromStep(directionsStep);
        assertEquals(transitDetails.numStops, directionWrapper.getTransitDetails().numStops);
    }
}