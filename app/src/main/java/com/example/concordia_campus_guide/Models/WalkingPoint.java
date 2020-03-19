package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesConverter;
import com.example.concordia_campus_guide.Database.Converters.EnumToStringConverter;
import com.example.concordia_campus_guide.Database.Converters.IntegerListToStringConverter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "walkingPoints",
        foreignKeys = {
                @ForeignKey(
                        entity = Floor.class,
                        parentColumns = {"floor_code"},
                        childColumns = {"floor_code"},
                        onDelete = CASCADE
                ),
        })
public  class WalkingPoint {

    @NonNull
    @ColumnInfo (name = "id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "coordinate")
    @TypeConverters(CoordinatesConverter.class)
    private Coordinates coordinate;

    @NonNull
    @ColumnInfo(name = "floor_code")
    private String floorCode;

    @ColumnInfo(name = "connected_points_ids")
    @TypeConverters(IntegerListToStringConverter.class)
    private List<Integer> connectedPointsId;

    @ColumnInfo(name = "point_type")
    @TypeConverters(EnumToStringConverter.class)
    private PointType pointType;

    public WalkingPoint(@NonNull Coordinates coordinate, @NonNull String floorCode, List<Integer> connectedPointsId, PointType pointType) {
        this.coordinate = coordinate;
        this.floorCode = floorCode;
        this.connectedPointsId = connectedPointsId;
        this.pointType = pointType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, floorCode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Coordinates getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(@NonNull Coordinates coordinate) {
        this.coordinate = coordinate;
    }

    @NonNull
    public String getFloorCode() { return floorCode; }

    public void setFloorCode(@NonNull String floorCode) {
        this.floorCode = floorCode;
    }

    public List<Integer> getConnectedPointsId() {
        return connectedPointsId;
    }

    public void setConnectedPointsId(List<Integer> connectedPointsId) { this.connectedPointsId = connectedPointsId; }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkingPoint that = (WalkingPoint) o;
        return coordinate.equals(that.coordinate) &&
                floorCode.equals(that.floorCode);
    }

    @Override
    public String toString() {
        return "WalkingPoint{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", floorCode='" + floorCode + '\'' +
                ", connectedPointsId=" + connectedPointsId +
                ", pointType=" + pointType +
                '}';
    }
}