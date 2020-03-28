package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Subway;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubwayTest {
    private Subway subway;
    private TestUtilsRoutes testUtils;

    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
    }

    @Test
    public void getColorTest() {
        // Arrange
        subway = new Subway(testUtils.directionsStepSubway());
        String expectedColor = testUtils.directionsStepSubway().transitDetails.line.color;

        // Act & Assert
        assertEquals(expectedColor, subway.getColor());
    }
}
