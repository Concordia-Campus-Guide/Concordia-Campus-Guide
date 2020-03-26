package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.Models.Routes.Bus;
import com.example.concordia_campus_guide.Models.Routes.Car;
import com.example.concordia_campus_guide.Models.Routes.Subway;
import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.google.android.gms.maps.model.LatLng;

public class Direction {
    private LatLng start;
    private LatLng end;
    private TransportType transportType;
    private String description;
    private double duration;
    private double distance;
    private String durationText;
    private String distanceText;

    public Direction(LatLng start, LatLng end, TransportType transportType, String description, double distance, double duration) {
        this.start = start;
        this.end = end;
        this.transportType = transportType;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
    }

    public Direction(DirectionsStep step)
    {
        this.start = new LatLng(step.startLocation.lat, step.startLocation.lng);
        this.end = new LatLng(step.endLocation.lat, step.endLocation.lng);
        this.transportType = getTransitType(step);
        this.description = step.htmlInstructions;
        this.duration = step.duration.value;
        this.distance = step.distance.value;
        this.durationText = step.duration.text;
    }

    private TransportType getTransitType(DirectionsStep step) {
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

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
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





