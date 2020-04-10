package com.example.concordia_campus_guide.ModelTests;


import com.example.concordia_campus_guide.models.BusStop;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BusStopTest {
    BusStop busStop;

    @Before
    public void init() {
        this.busStop = new BusStop("SGW");
    }

    @Test
    public void getDisplayName() {
        assertEquals("Shuttle SGW stop", busStop.getDisplayName());
    }
}
