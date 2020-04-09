package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.models.Routes.Car;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CarTest {
    private Car car;

    @Test
    public void getDurationTest() {
        // Arrange
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.duration = new Duration();
        directionsStep.duration.value = 142;

        car = new Car(directionsStep);

        // Act & Assert
        assertEquals(142, car.getDuration());
    }
}
