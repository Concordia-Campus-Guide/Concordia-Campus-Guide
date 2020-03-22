package com.example.concordia_campus_guide.Adapters;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.Models.Direction;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

public class DirectionWrapper {
    Direction direction;
    Polyline polyline;

    public DirectionWrapper(DirectionsStep directionStep){
        // create a new direction
        direction = new Direction();
        direction.setStart(new LatLng(directionStep.startLocation.lat, directionStep.startLocation.lng));
        direction.setDescription(directionStep.htmlInstructions);

        // map polyline
    }
}
