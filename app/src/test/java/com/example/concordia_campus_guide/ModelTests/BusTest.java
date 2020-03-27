package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.Models.Routes.Bus;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BusTest {
    private Bus bus;
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getBusNumberTest() {
        bus = new Bus(testUtils.getDirectionStepsObject());
        assertEquals(testUtils.getDirectionStepsObject().transitDetails.line.shortName,bus.getBusNumber());
    }
}
