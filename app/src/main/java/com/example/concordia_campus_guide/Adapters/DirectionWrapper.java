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

    public DirectionWrapper(DirectionsStep directionStep){
        // create a new direction
        direction = new Direction();
        direction.setStart(new LatLng(directionStep.startLocation.lat, directionStep.startLocation.lng));
        direction.setEnd(new LatLng(directionStep.endLocation.lat, directionStep.endLocation.lng));
        direction.setType(getTransitType(directionStep.travelMode));
        direction.setDescription(directionStep.htmlInstructions);
        direction.setDuration(directionStep.duration.value);

        // map polyline
        polyline = directionStep.polyline;
    }

    private TransitType getTransitType(TravelMode travelMode){
        switch (travelMode){
            case DRIVING:
                return TransitType.CAR;
            case WALKING:
                return TransitType.WALK;
            case BICYCLING:
                return TransitType.BIKE;
            case TRANSIT:
                // TODO: we have both metro and bus (but Google only provides TRANSIT)
                return TransitType.BUS;
        }
        // TODO: what should be returned if there is no match?
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
