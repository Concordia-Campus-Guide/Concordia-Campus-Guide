package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;

public class Bus implements TransportType {

    private String busNumber;

    public Bus(DirectionsStep step) {
        busNumber = step.transitDetails.line.shortName;
    }

    public String getBusNumber() {
        return busNumber;
    }
}
