package com.example.concordia_campus_guide.Adapters;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.EncodedPolyline;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.Models.Direction;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class DirectionWrapper implements Serializable {
    Direction direction;
    EncodedPolyline polyline;

    public DirectionWrapper(DirectionsStep directionStep){
        // create a new direction
        direction = new Direction();
        direction.setStart(new LatLng(directionStep.startLocation.lat, directionStep.startLocation.lng));
        direction.setEnd(new LatLng(directionStep.endLocation.lat, directionStep.endLocation.lng));
        direction.setTransportType(getTransitType(directionStep.travelMode));
        direction.setDescription(directionStep.htmlInstructions);
        direction.setDuration(directionStep.duration.value);

        // map polyline
        polyline = directionStep.polyline;
    }

    private String getTransitType(TravelMode travelMode){
        switch (travelMode){
            case DRIVING:
                return ClassConstants.DRIVING;
            case WALKING:
                return ClassConstants.WALKING;
            case BICYCLING:
                return ClassConstants.BICYCLING;
            case TRANSIT:
                return ClassConstants.TRANSIT;
        }
        return null;
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
