package com.example.concordia_campus_guide.ModelTests.TestUtils;

import com.example.concordia_campus_guide.models.Shuttle;

import java.util.Arrays;

public class TestUtilsRoutes {

    // Shuttle
    public Shuttle getShuttle1(){
        Shuttle shuttle1 = new Shuttle();
        shuttle1.setCampus("SGW");
        shuttle1.setDay(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday","Friday"));
        shuttle1.setShuttleId(1);
        shuttle1.setTime(8.20);
        return shuttle1;
    }

    public Shuttle getShuttle2(){
        Shuttle shuttle2 = new Shuttle();
        shuttle2.setCampus("LOY");
        shuttle2.setDay(Arrays.asList("Monday", "friday"));
        shuttle2.setShuttleId(2);
        shuttle2.setTime(9.10);
        return  shuttle2;
    }
}
