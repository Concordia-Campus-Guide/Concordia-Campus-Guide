package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "rooms",
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = "number",
                        childColumns = "floor_number",
                        onDelete = CASCADE
                )},
        primaryKeys = {"room_number","floor_number"})
class Room extends Place {
    @ColumnInfo(name = "room_number")
    String number;

    @ColumnInfo(name = "floor_number")
    String floorNumber;

    public Room(Double[] coordinates, String number) {
        super(coordinates);
        this.number = number;
    }
}
