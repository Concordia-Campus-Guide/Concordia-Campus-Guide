package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;

class Room extends Place {
    String number;

    public Room(Double[] coordinates, String number) {
        super(coordinates);
        this.number = number;
    }
}
