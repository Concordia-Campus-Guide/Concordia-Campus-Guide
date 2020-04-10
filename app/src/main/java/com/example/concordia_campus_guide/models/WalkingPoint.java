package com.example.concordia_campus_guide.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.database.converters.IntegerListToStringConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
public  class WalkingPoint implements Serializable {

    @NonNull
    @ColumnInfo (name = "id")
    @PrimaryKey
    private int id;

    @Embedded
    private Coordinates coordinate;

    @NonNull
    @ColumnInfo(name = "floor_code")
    private String floorCode;

    @ColumnInfo(name = "connected_points_ids")
    @TypeConverters(IntegerListToStringConverter.class)
    private List<Integer> connectedPointsId;

    @ColumnInfo(name = "point_type")
    private @PointType String pointType;

    @ColumnInfo(name = "place_code")
    private String placeCode;


    public WalkingPoint(@NonNull Coordinates coordinate, @NonNull String floorCode, List<Integer> connectedPointsId, @PointType String pointType, String placeCode) {
        this.coordinate = coordinate;
        this.floorCode = floorCode;
        this.connectedPointsId = connectedPointsId;
        this.pointType = pointType;
        this.placeCode = placeCode;
    }

    @Ignore
    public WalkingPoint(@NonNull int id,@NonNull Coordinates coordinate, @NonNull String floorCode, List<Integer> connectedPointsId, @PointType String pointType, String placeCode) {
        this.id = id;
        this.coordinate = coordinate;
        this.floorCode = floorCode;
        this.connectedPointsId = connectedPointsId;
        this.pointType = pointType;
        this.placeCode = placeCode;
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

    public @PointType String getPointType() {
        return pointType;
    }

    public void setPointType(@PointType String pointType) {
        this.pointType = pointType;
    }

    public String getPlaceCode() { return placeCode; }

    public void setPlaceCode(String placeCode) { this.placeCode = placeCode; }

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