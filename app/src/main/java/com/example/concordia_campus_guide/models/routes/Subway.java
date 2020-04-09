package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;

public class Subway extends TransportType {
    private String color;

    public Subway(DirectionsStep step) {
        color = step.transitDetails.line.color;
    }

    public String getColor() {
        return color;
    }
}
