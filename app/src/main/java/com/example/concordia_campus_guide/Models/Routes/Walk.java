package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.R;

public class Walk extends TransportType {
    private String duration;

    public Walk(DirectionsStep step) {
        duration = step.duration.text;
    }

    public String getDuration() {
        return duration;
    }
}
