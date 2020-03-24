package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Car extends TransitType {
    private long duration; // in seconds

    public Car() {}

    public Car(DirectionsStep step) {
        duration = step.duration.value;
    }
}
