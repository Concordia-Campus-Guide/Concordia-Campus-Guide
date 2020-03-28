package com.example.concordia_campus_guide.ModelTests.TestUtils;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.Distance;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.Duration;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TransitLine;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.google.android.gms.maps.model.LatLng;

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
