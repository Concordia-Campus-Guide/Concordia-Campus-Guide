package com.example.concordia_campus_guide.Models.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.List;

public class FloorWithRooms {

    @Embedded
    private Floor floor;

    public FloorWithRooms() {
    }

    @Relation(
            parentColumn = "floor_code",
            entityColumn = "floor_code"
    )
    private List<RoomModel> rooms;

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }


}
