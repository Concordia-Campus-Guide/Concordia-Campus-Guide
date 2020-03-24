package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Subway extends TransitType {
    public String color;

    public Subway() {}

    public Subway(DirectionsStep step) {
        color = step.transitDetails.line.color;
    }
}
