package com.example.concordia_campus_guide.Adapters;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.EncodedPolyline;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.TransitType;
import com.google.android.gms.maps.model.LatLng;

public class DirectionWrapper {
    Direction direction;
    EncodedPolyline polyline;

    public DirectionWrapper(DirectionsStep step){
        // create a new direction
        direction = new Direction(step);

        // map polyline
        polyline = step.polyline;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public EncodedPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(EncodedPolyline polyline) {
        this.polyline = polyline;
    }
}
