package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Bus extends TransportType {

    private String busNumber;

    public Bus(DirectionsStep step) {
        busNumber = step.transitDetails.line.shortName;
    }

    public String getBusNumber() {
        return busNumber;
    }
}
