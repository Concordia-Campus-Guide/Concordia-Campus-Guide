package com.example.concordia_campus_guide.Models.Helpers;

import java.io.Serializable;

public class PlaceIdAndPlaceType implements Serializable {
    long placeId;
    String placeType;

    public PlaceIdAndPlaceType(long placeId, String placeType){
        this.placeId = placeId;
        this.placeType = placeType;
    }
}
