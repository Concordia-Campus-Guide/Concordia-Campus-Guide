package com.example.concordia_campus_guide.models.Routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Car extends TransportType {

    private long duration; // in seconds

    public Car(DirectionsStep step) {
        duration = step.duration.value;
    }

    public long getDuration() { return duration; }
}
