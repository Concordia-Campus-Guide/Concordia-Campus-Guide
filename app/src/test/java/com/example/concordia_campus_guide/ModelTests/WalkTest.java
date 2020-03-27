package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.DirectionStepsJson;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Models.Shuttles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class WalkTest {
    private Walk walk;
    private DirectionStepsJson directionStepsJson;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        walk = new Walk();
        directionStepsJson = new DirectionStepsJson();
        DirectionStepsJson.retrieveBody("test_walking_points.json");
        String s = "Hello;";
    }

    @Test
    public void testShuttleCampus() {
        assertNotNull(null);
    }
}
