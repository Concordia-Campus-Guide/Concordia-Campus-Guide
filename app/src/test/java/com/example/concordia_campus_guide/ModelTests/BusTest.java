package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Bus;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BusTest {
    private Bus bus;

    @Test
    public void getBusNumberTest() {
        // Arrange
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.shortName = "128";

        bus = new Bus(directionsStep);

        // Act & Assert
        assertEquals("128", bus.getBusNumber());
    }
}
