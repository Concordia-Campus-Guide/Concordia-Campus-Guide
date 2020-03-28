package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Car;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CarTest {
    private Car car;
    private TestUtilsRoutes testUtils;

    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
    }

    @Test
    public void getDurationTest() {
        // Arrange
        car = new Car(testUtils.directionsStepCar());
        long expectedDuration = testUtils.directionsStepCar().duration.value;

        // Act & Assert
        assertEquals(expectedDuration, car.getDuration());
    }
}
