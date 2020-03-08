package com.example.concordia_campus_guide.Models;

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

    public Coordinates() {
    }

    public List<Double> toListDouble(){
        return new ArrayList<>(Arrays.asList(latitude, longitude));
    }
}
