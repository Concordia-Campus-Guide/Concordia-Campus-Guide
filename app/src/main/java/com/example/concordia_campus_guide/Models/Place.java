package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    protected Double[] centerCoordinates;

    public Place(Double[] centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public Double[] getCenterCoordinates() {
        return centerCoordinates;
    }

    public void setCenterCoordinates(Double[] centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }
}
