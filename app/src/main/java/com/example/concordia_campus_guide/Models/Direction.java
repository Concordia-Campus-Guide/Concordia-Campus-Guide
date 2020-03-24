package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TravelMode;
import com.google.android.gms.maps.model.LatLng;

public class Direction {
    private LatLng start;
    private LatLng end;
    private TransitType transitType;
    private String description;
    private double duration;

    public Direction(LatLng start, LatLng end, TransitType transitType, String description, double duration) {
        this.start = start;
        this.end = end;
        this.transitType = transitType;
        this.description = description;
        this.duration = duration;
    }

    public Direction(DirectionsStep step)
    {
        this.start = new LatLng(step.startLocation.lat, step.startLocation.lng);
        this.end = new LatLng(step.endLocation.lat, step.endLocation.lng);
        this.transitType = getTransitType(step);
        this.description = step.htmlInstructions;
        this.duration = step.duration.value;
    }

    private TransitType getTransitType(DirectionsStep step) {
        TravelMode mode = step.travelMode;
        switch (mode) {
            case DRIVING:
                return new Car(step);
            case WALKING:
                return new Walk(step);
            case BICYCLING:
                return null;
            case TRANSIT:
                if(step.transitDetails.line.vehicle.name.equalsIgnoreCase("bus")) {
                    return new Bus(step);
                }
                else if (step.transitDetails.line.vehicle.name.equalsIgnoreCase("subway")) {
                    return new Subway(step);
                }
        }
        return null;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

    public TransitType getTransitType() {
        return transitType;
    }

    public void setTransitType(TransitType transitType) {
        this.transitType = transitType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

}





