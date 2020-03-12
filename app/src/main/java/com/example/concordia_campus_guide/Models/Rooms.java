package com.example.concordia_campus_guide.Models;

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
}
