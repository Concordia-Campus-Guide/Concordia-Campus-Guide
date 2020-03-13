package com.example.concordia_campus_guide.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "rooms",
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = {"floor_code"},
                        childColumns = {"floor_code"},
                        onDelete = CASCADE
                ),
                },
        indices = {@Index(value = {"room_code"},
        unique = true)})
public class RoomModel extends Place {

    @ColumnInfo(name = "room_code")
    @NonNull
    private String roomCode;

    @ColumnInfo(name = "floor_code")
    @NonNull
    private String floorCode;

    @NonNull
    public String getFloorCode() {
        return floorCode;
    }

    @NonNull
    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(@NonNull String number) {
        this.roomCode = number;
    }

    public RoomModel(){
        super();
    }

    public void setFloorCode(@NonNull String floorCode) {
        this.floorCode = floorCode;
    }

    public String getDisplayName(){
        return roomCode;
    }
}
