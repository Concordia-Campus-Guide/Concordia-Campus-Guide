package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Routes.Car;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CarTest {
    private Car car;
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getDurationTest() {
        car = new Car(testUtils.getDirectionStepsObject());
        assertEquals(testUtils.getDirectionStepsObject().duration.value,car.getDuration());
    }
}
