package com.example.concordia_campus_guide.ModelTests;
import com.example.concordia_campus_guide.Models.Routes.Walk;


import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


import static junit.framework.TestCase.assertEquals;

public class WalkTest {
    private Walk walk;
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils = new TestUtils();
    }

    @Test
    public void getDurationTest() {
        walk = new Walk(testUtils.getDirectionStepsObject());
        assertEquals(testUtils.getDirectionStepsObject().duration.text,walk.getDuration());
    }

    @Test
    public void setDurationTest(){
        walk = new Walk();
        walk.setDuration("2 mins");
        assertEquals(testUtils.getDirectionStepsObject().duration.text,walk.getDuration());
    }
}
