package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;

public class Train extends TransportType {

    private String trainShortName;

    public Train(DirectionsStep step) {
        trainShortName = step.transitDetails.line.shortName;
    }

    public String getTrainShortName() {
        return trainShortName;
    }

}
