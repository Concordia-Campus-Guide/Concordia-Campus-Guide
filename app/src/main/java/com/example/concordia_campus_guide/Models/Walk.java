package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Walk extends TransitType {
    private String duration;

    public Walk() {}

    public Walk(DirectionsStep step) {
        duration = step.duration.text;
    }
}
