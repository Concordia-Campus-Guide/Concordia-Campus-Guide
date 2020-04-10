package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;

public class Car implements TransportType {

    private long duration; // in seconds

    public Car(DirectionsStep step) {
        duration = step.duration.value;
    }

    public long getDuration() { return duration; }
}
