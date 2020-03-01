package com.example.concordia_campus_guide.Models;

class Room extends Place {
    String number;

    public Room(Double[] coordinates, String number) {
        super(coordinates);
        this.number = number;
    }

    public String getDisplayName(){
        return this.number;
    }
}
