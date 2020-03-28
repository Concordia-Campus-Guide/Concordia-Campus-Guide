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

    // Directions step
    public DirectionsStep directionsStepBus(){
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.shortName = "128";

        return directionsStep;
    }

    public DirectionsStep directionsStepCar(){
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.duration = new Duration();
        directionsStep.duration.value = 142;

        return directionsStep;
    }

    public DirectionsStep directionsStepSubway(){
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.color = "orange";

        return directionsStep;
    }

    public DirectionsStep directionsStepTrain(){
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.transitDetails = new TransitDetails();
        directionsStep.transitDetails.line = new TransitLine();
        directionsStep.transitDetails.line.shortName = "DM";

        return directionsStep;
    }

    public DirectionsStep directionsStepWalk(){
        DirectionsStep directionsStep = new DirectionsStep();
        directionsStep.duration = new Duration();
        directionsStep.duration.text  = "2 mins";

        return directionsStep;
    }

    // Direction
    public Direction direction = new Direction();
//            new LatLng(-73.57907921075821, 45.49702057370776),
//            new LatLng(-73.57921063899994, 45.49707133596979),
//            new Walk(getDirectionStepsObject()), "This is a description");

    // Routes
    public Route route1 = new Route("1 day 20 hours", "I-80 E", "driving");

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
