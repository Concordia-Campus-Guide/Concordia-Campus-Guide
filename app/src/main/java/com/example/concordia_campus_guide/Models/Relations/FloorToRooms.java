package com.example.concordia_campus_guide.Models.Relations;

import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FloorToRooms {

    @Embedded
    private Floor floor;

    public FloorToRooms() {
    }

    @Relation(
            parentColumn = "floor_number",
            entityColumn = "floor_number",
            entity = Floor.class
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
