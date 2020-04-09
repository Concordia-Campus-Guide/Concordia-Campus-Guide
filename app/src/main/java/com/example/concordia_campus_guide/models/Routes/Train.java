package com.example.concordia_campus_guide.models.Routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Train extends TransportType {

    private String trainShortName;

    public Train(DirectionsStep step) {
        trainShortName = step.transitDetails.line.shortName;
    }

    public String getTrainShortName() {
        return trainShortName;
    }

}
