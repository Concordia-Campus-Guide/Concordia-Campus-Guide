package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;

public class Walk implements TransportType {
    private String duration;

    public Walk(DirectionsStep step) {
        duration = step.duration.text;
    }

    public String getDuration() {
        return duration;
    }
}
