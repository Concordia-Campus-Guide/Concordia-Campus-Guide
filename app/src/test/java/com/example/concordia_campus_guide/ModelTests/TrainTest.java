package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.models.routes.Train;

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
