package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Bus extends TransitType {
    private String busNumber;

    public Bus(DirectionsStep step) {
        busNumber = step.transitDetails.line.shortName;
    }
}
