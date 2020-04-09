package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.models.Routes.Train;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TrainTest {
    private Train train;
    @Test
    public void getColorTest() {
        // Act
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.shortName = "DM";

        train = new Train(directionsStep);

        // Arrange & Assert
        assertEquals("DM", train.getTrainShortName());
    }
}
