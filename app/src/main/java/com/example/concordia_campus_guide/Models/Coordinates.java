package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coordinates {

    double latitude;
    double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return latitude == that.latitude  &&
                longitude == that.longitude;
    }

    public List<Double> toListDouble(){
        return new ArrayList<>(Arrays.asList(latitude, longitude));
    }

    public List<Double> toListDoubleLongLat(){
        return new ArrayList<>(Arrays.asList(longitude, latitude));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
