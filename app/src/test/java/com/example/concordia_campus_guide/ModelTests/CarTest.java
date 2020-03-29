package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Car;

import org.junit.Before;
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
