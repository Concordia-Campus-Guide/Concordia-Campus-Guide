package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.Models.Routes.Train;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TrainTest {
    private Train train;
    private TestUtilsRoutes testUtils;

    @Before
    public void init() {
        testUtils = new TestUtilsRoutes();
    }

    @Test
    public void getColorTest() {
        // Act
        train = new Train(testUtils.directionsStepTrain());
        String expectedTrainShortName = testUtils.directionsStepTrain().transitDetails.line.shortName;

        // Arrange & Assert
        assertEquals(expectedTrainShortName, train.getTrainShortName());
    }
}
