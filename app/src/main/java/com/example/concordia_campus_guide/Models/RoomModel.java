package com.example.concordia_campus_guide.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "rooms",
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = {"floor_number","building_code"},
                        childColumns = {"floor_number","building_code"},
                        onDelete = CASCADE
                ),
                },
        primaryKeys = {"room_number","floor_number","building_code"},
        indices = {@Index(value = {"room_number"},
        unique = true)})
public class RoomModel extends Place {
    @ColumnInfo(name = "room_number")
    @NonNull
    private String number;

    @ColumnInfo(name = "floor_number")
    @NonNull
    private String floorNumber;



    @ColumnInfo (name = "building_code")
    @NonNull
    private String buildingCode;

    @NonNull
    public String getNumber() {
        return number;
    }



    @NonNull
    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(@NonNull String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }
    public String getBuidlingCode() {
        return buildingCode;
    }

    public void setBuidlingCode(String buidlingCode) {
        this.buildingCode = buidlingCode;
    }
    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }



    public RoomModel(){
        super();
    }
    public RoomModel(Double[] coordinates, String number) {
        super(coordinates);
        this.number = number;
    }

}
