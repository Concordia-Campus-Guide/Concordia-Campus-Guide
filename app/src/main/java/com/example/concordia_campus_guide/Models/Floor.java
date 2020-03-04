package com.example.concordia_campus_guide.Models;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "floors",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                parentColumns = "buildingCode",
                childColumns = "buildingCode",
                onDelete = CASCADE
        )},
        primaryKeys = {"buildingCode","number"})
public class Floor extends Place {

    @ColumnInfo(name = "buildingCode")
    private String buildingCode;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "altitude")
    private float altitude;


    //Not sure I used foreign key in the room class
    @Ignore
    private List<Room> rooms;

    public Floor(Double[] coordinates, String number, float altitude) {
        super(coordinates);
        this.number = number;
        this.altitude = altitude;
    }

}

