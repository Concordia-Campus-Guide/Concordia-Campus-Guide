package com.example.concordia_campus_guide.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "room_id")
    private Integer roomId;

    @ColumnInfo(name = "room_code")
    @NonNull
    private String roomCode;

    public RoomModel(Double[] centerCoordinates, @NonNull String roomCode, @NonNull String floorCode) {
        super(centerCoordinates);
        this.roomCode = roomCode;
        this.floorCode = floorCode;
    }

    @ColumnInfo(name = "floor_code")
    @NonNull
    private String floorCode;




    public Integer getRoomId() {
        return roomId;
    }

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

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public void setFloorCode(@NonNull String floorCode) {
        this.floorCode = floorCode;
    }
}


