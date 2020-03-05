package com.example.concordia_campus_guide.Models;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "floors",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                parentColumns = "building_code",
                childColumns = "building_code",
                onDelete = CASCADE
        )},
        primaryKeys = {"building_code","floor_number"},
        indices = {@Index(value = {"building_code","floor_number"},
                unique = true)})
public class Floor extends Place {

    @NonNull
    @ColumnInfo(name = "building_code")
    private String buildingCode;

    @NonNull
    @ColumnInfo(name = "floor_number")
    private String number;

    @ColumnInfo(name = "altitude")
    private float altitude;


    //Not sure I used foreign key in the room class
    @Ignore
    private List<RoomModel> rooms;

    public Floor() {
        super();
    }
    public Floor(Double[] coordinates, String number, float altitude) {
        super(coordinates);
        this.number = number;
        this.altitude = altitude;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }

}

