package com.example.concordia_campus_guide.Models;

public class MyCurrentPlace extends Place {
    // TODO: add to strings.xml (find a way to access without context)
    String displayName = "Select location";

    public MyCurrentPlace(Double latitude, Double longitude){
        this.setCenterCoordinates(new Coordinates(latitude, longitude));
        this.displayName = latitude.toString() + ", " + longitude.toString();
    }

    public MyCurrentPlace(){}

    public String getDisplayName(){
        return this.displayName;
    }
}
