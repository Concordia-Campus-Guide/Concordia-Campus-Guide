package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Walk;


import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;

public class WalkTest {
    private Walk walk;
    private TestUtilsRoutes testUtils;

    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
    }

    @Test
    public void getDurationTest() {
        // Arrange
        walk = new Walk(testUtils.directionsStepWalk());
        String expectedDuration = testUtils.directionsStepWalk().duration.text;

        // Act & Assert
        assertEquals(expectedDuration, walk.getDuration());
    }
}
