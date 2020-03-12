package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesListConverter;
import com.example.concordia_campus_guide.Database.Converters.WalkingPointListConverter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

//Nodes of our search graph
@Entity(tableName = "walkingpoints",
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = {"floor_code"},
                        childColumns = {"floor_code"},
                        onDelete = CASCADE
                ),
        },
        primaryKeys = {"floor_code","coordinate"},
        indices = {@Index(value = {"coordinate"},
                unique = true)})
public class WalkingPoint {

    @NonNull
    @ColumnInfo(name = "coordinate")
    private Coordinates coordinate;

    @NonNull
    @ColumnInfo(name = "floor_code")
    private String floor_code;

    @ColumnInfo(name = "connectedPoints")
    @TypeConverters(CoordinatesListConverter.class)
    private List<Coordinates> connectedPoints;

    @ColumnInfo(name = "connectedAccessPoint")
    private List<WalkingPoint> connectedAccessPoint;
    // This will be a list connecting it to other accessPoints on other floors ...
    // If this is null, then we know that this WalkingPoint is NOT an accessPoint.


    public WalkingPoint(@NonNull Coordinates coordinate, @NonNull String floor_code, List<Coordinates> connectedPoints, List<WalkingPoint> connectedAccessPoint) {
        this.coordinate = coordinate;
        this.floor_code = floor_code;
        this.connectedPoints = connectedPoints;
        this.connectedAccessPoint = connectedAccessPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkingPoint that = (WalkingPoint) o;
        return coordinate.equals(that.coordinate) &&
                floor_code.equals(that.floor_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, floor_code);
    }
}