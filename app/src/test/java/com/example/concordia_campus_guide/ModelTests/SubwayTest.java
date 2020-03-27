package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Routes.Subway;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubwayTest {
    private Subway subway;
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getColorTest() {
        subway = new Subway(testUtils.getDirectionStepsObject());
        assertEquals(testUtils.getDirectionStepsObject().transitDetails.line.color,subway.getColor());
    }

    @Test
    public void setColorTest(){
        subway = new Subway();
        subway.setColor("orange");
        assertEquals(testUtils.getDirectionStepsObject().transitDetails.line.color,subway.getColor());
    }
}
