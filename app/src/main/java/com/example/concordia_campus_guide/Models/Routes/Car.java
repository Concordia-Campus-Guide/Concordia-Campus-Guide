package com.example.concordia_campus_guide.Models.Routes;

import androidx.annotation.VisibleForTesting;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Car extends TransportType {

    private long duration; // in seconds

    @VisibleForTesting
    public Car() {

    }

    public Car(DirectionsStep step) {
        duration = step.duration.value;
    }

    public long getDuration() { return duration; }
}
