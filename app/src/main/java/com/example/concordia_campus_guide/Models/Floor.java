package com.example.concordia_campus_guide.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.example.concordia_campus_guide.R;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "floors",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                parentColumns = "building_code",
                childColumns = "building_code",
                onDelete = CASCADE
        )},
        indices = {@Index(value = {"floor_code"},
                unique = true)})
public class Floor extends Place {

    @NonNull
    @ColumnInfo(name = "building_code")
    private String buildingCode;

    //due to Room ORM constraints, we make a composite key built-in with the building_code and
    //the floor indicator/number
    //the floor code is the BUILDING_CODE-FLOOR-INDICATOR ie: H-9
    @NonNull
    @ColumnInfo(name = "floor_code")
    private String floorCode;

    @ColumnInfo(name = "altitude")
    private float altitude;

    public Floor() {
        super();
    }
    public Floor(Coordinates coordinates, String floorCode, float altitude) {
        super(coordinates);
        this.floorCode = floorCode;
        this.altitude = altitude;
    }

    public Floor(Coordinates coordinates, String floorCode, float altitude, String campus) {
        super(coordinates, campus);
        this.floorCode = floorCode;
        this.altitude = altitude;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getDisplayName(){
        return floorCode;
    }
}

