package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.models.Routes.Walk;


import org.junit.Test;


import static junit.framework.TestCase.assertEquals;

public class WalkTest {
    private Walk walk;

    @Test
    public void getDurationTest() {
        // Arrange
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.duration = new Duration();
        directionsStep.duration.text  = "2 mins";

        walk = new Walk(directionsStep);

        // Act & Assert
        assertEquals("2 mins", walk.getDuration());
    }
}
