package com.example.concordia_campus_guide.Models;

public class MyCurrentPlace extends Place {

    String displayName = "My location";

    public MyCurrentPlace(Double latitude, Double longitude){
        this.setCenterCoordinates(new Double[]{latitude, longitude});
        this.displayName = latitude.toString() + ", " + longitude.toString();
    }

    public String getDisplayName(){
        return this.displayName;
    }
}
