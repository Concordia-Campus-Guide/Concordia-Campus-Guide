package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.models.routes.Route;
import com.example.concordia_campus_guide.models.routes.TransportType;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

public class RouteTest {

    @Test
    public void getDepartureTimeTest() {
        // Arrange
        Route route = new Route(null, "4:23pm", "4:33pm", "10 min", "transit", ClassConstants.TRANSIT);
        String expectedDepartureTime = "4:23pm";

        // Act & Arrange
        assertEquals(expectedDepartureTime, route.getDepartureTime());

    }

    @Test
    public void getArrivalTimeTest() {
        // Arrange
        Route route = new Route(null, "4:23pm", "4:33pm", "10 min", "transit", "");
        String expectedArrivalTime = "4:33pm";

        // Act & Arrange
        assertEquals(expectedArrivalTime, route.getArrivalTime());
    }

    @Test
    public void getDurationTest() {
        // Arrange
        Route route = new Route(null,"", "", "1 hour 20 min", "transit", "");
        String expectedDuration = "1 hour 20 min";

        // Act & Arrange
        assertEquals(expectedDuration, route.getDuration());
    }

    @Test
    public void getSummaryTest() {
        // Arrange
        Route route = new Route(null,"", "", "1 hour 20 min", "I-80 E", "");
        String expectedSummary = "I-80 E";

        // Act & Arrange
        assertEquals(expectedSummary, route.getSummary());
    }

    @Test
    public void getMainTransportTypeTest() {
        // Arrange
        Route route = new Route(null,"", "", "1 hour 20 min", "I-80 E", "driving");
        String expectedMainTransportType = "driving";

        // Act & Arrange
        assertEquals(expectedMainTransportType, route.getMainTransportType());
    }

    @Test
    public void getStepsTest() {
        // Arrange
        List<TransportType> expectedSteps = new ArrayList<>();
        Route route = new Route(expectedSteps,"", "", "1 hour 20 min", "I-80 E", "driving");

        // Act & Assert
        assertEquals(expectedSteps, route.getSteps());
    }
}
