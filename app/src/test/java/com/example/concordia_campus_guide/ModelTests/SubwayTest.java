package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.models.routes.Subway;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubwayTest {
    private Subway subway;

    @Test
    public void getColorTest() {
        // Arrange
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.color = "orange";

        subway = new Subway(directionsStep);

        // Act & Assert
        assertEquals("orange", subway.getColor());
    }
}
