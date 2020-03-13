package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coordinates {

    double latitude;
    double longitude;

    public Coordinates() { }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public List<Double> toListDouble(){
        return new ArrayList<>(Arrays.asList(latitude, longitude));
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
