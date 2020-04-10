package com.example.concordia_campus_guide.adapters;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.EncodedPolyline;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TransitDetails;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.TravelMode;
import com.example.concordia_campus_guide.models.Direction;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class DirectionWrapper implements Serializable {
    private Direction direction;
    private EncodedPolyline polyline;
    private TransitDetails transitDetails;

    public void populateAttributesFromStep(DirectionsStep directionStep) {
        // create a new direction
        this.direction = new Direction();
        this.direction.setStart(new LatLng(directionStep.startLocation.lat, directionStep.startLocation.lng));
        this.direction.setEnd(new LatLng(directionStep.endLocation.lat, directionStep.endLocation.lng));
        this.direction.setTransportType(getTransitType(directionStep.travelMode));
        this.direction.setDescription(directionStep.htmlInstructions);
        this.direction.setDuration(directionStep.duration.value);

        // map polyline
        this.polyline = directionStep.polyline;

        //get color is of type transit
        if (directionStep.travelMode.toString().equals(ClassConstants.TRANSIT)) {
            this.transitDetails = directionStep.transitDetails;
        }
    }

    public DirectionWrapper() {
        // being used in tests
    }

    private String getTransitType(TravelMode travelMode) {
        return travelMode.name().toLowerCase();
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

    public TransitDetails getTransitDetails() {
        return transitDetails;
    }

}
