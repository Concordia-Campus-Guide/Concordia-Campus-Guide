package com.example.concordia_campus_guide.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "rooms",
        indices = {@Index(value = {"floor_code", "room_code"},
                unique = true)},
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = {"floor_code"},
                        childColumns = {"floor_code"},
                        onDelete = CASCADE
                ),
        })
public class RoomModel extends Place implements Serializable {

    @ColumnInfo(name = "room_code")
    @NonNull
    private String roomCode;

    public RoomModel(Coordinates centerCoordinates, @NonNull String roomCode, @NonNull String floorCode, String campus) {
        super(centerCoordinates, campus);
        this.roomCode = roomCode;
        this.floorCode = floorCode;
    }

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

    public RoomModel() {
        super();
    }

    public JSONObject getGeoJson() {
        JSONObject toReturn = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject geometry = new JSONObject();

        try {
            toReturn.put("type", "Feature");

            properties.put("floorCode", floorCode);
            if (roomCode != null) properties.put("roomCode", roomCode);
            toReturn.put("properties", properties);

            geometry.put("type", "Point");
            Double[] geoJsonCoordinates = this.getCenterCoordinatesDoubleArray();
            geometry.put("coordinates", new JSONArray(geoJsonCoordinates));

            toReturn.put("geometry", geometry);
        } catch (Exception e) {
            Log.e("RoomModel", e.getMessage());
        }

        return toReturn;
    }

    public void setFloorCode(@NonNull String floorCode) {
        this.floorCode = floorCode;
    }

    public String getDisplayName() {
        return floorCode + " " + roomCode;
    }
}