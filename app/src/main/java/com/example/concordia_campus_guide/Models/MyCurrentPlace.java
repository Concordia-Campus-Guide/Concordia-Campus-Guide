package com.example.concordia_campus_guide.Models;

public class MyCurrentPlace extends Place {

    String displayName = "Select location";

    public MyCurrentPlace(Double latitude, Double longitude){
        this.setCenterCoordinates(new Coordinates(latitude, longitude));
        this.displayName = "My Current Location";
    }

    public MyCurrentPlace(){}

    public String getDisplayName(){
        return this.displayName;
    }
}
