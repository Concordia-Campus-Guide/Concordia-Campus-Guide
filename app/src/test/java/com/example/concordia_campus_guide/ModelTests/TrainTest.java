package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Routes.Subway;
import com.example.concordia_campus_guide.Models.Routes.Train;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TrainTest {
    private Train train;
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getColorTest() {
        train = new Train(testUtils.getDirectionStepsObject());
        assertEquals(testUtils.getDirectionStepsObject().transitDetails.line.shortName,train.getTrainShortName());
    }
}
