package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsStep;

public class Train implements TransportType {

    private String trainShortName;

    public Train(DirectionsStep step) {
        trainShortName = step.transitDetails.line.shortName;
    }

    public String getTrainShortName() {
        return trainShortName;
    }

}
