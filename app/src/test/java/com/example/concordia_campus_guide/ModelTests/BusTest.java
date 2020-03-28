package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Bus;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BusTest {
    private Bus bus;
    private TestUtilsRoutes testUtils;

    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
    }

    @Test
    public void getBusNumberTest() {
        // Arrange
        bus = new Bus(testUtils.directionsStepBus());
        String expectedBusNumber = testUtils.directionsStepBus().transitDetails.line.shortName;

        // Act & Assert
        assertEquals(expectedBusNumber, bus.getBusNumber());
    }
}
