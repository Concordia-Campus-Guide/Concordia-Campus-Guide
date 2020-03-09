package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Rooms {

    private List<RoomModel> Rooms;

    public Rooms(){}

    public Rooms(List<RoomModel> rooms) {
        Rooms = rooms;
    }

    public List<RoomModel> getRooms() {
        return Rooms;
    }

    public List<Place> getPlaces(){
        List<Place> toReturn = new ArrayList<Place>();
        for(RoomModel room: Rooms){
            toReturn.add(room);
        }
        return toReturn;
    }
}
