package com.example.concordia_campus_guide.models.Routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Walk extends TransportType {
    private String duration;

    public Walk(DirectionsStep step) {
        duration = step.duration.text;
    }

    public String getDuration() {
        return duration;
    }
}
