package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Subway extends TransportType {
    private String color;

    public Subway() {}

    public Subway(DirectionsStep step) {
        color = step.transitDetails.line.color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
