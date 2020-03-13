package com.example.concordia_campus_guide.Models;

import android.text.TextUtils;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesListConverter;
import com.example.concordia_campus_guide.Database.Converters.EnumToStringConverter;
import com.example.concordia_campus_guide.Database.Converters.WalkingPointListConverter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
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
    private String floorCode;

    @ColumnInfo(name = "connected_points")
    @TypeConverters(CoordinatesListConverter.class)
    private ListOfCoordinates connectedPoints;

    @ColumnInfo(name = "accessibility_type")
    @TypeConverters(EnumToStringConverter.class)
    private AccessibilityType accessibilityType;

//    @ColumnInfo(name = "connected_access_point")
//    private List<WalkingPoint> connectedAccessPoint;

    // This will be a list connecting it to other accessPoints on other floors ...
    // If this is null, then we know that this WalkingPoint is NOT an accessPoint.


    public WalkingPoint(@NonNull Coordinates coordinate, @NonNull String floorCode, ListOfCoordinates connectedPoints, AccessibilityType accessibilityType) {
        this.coordinate = coordinate;
        this.floorCode = floorCode;
        this.connectedPoints = connectedPoints;
        this.accessibilityType = accessibilityType;
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
    public int hashCode() {
        return Objects.hash(coordinate, floorCode);
    }


    public JSONObject getGeoJson(){
        JSONObject toReturn = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject geometry = new JSONObject();

        try{
            if(coordinate!=null) properties.put("StartingPoint", coordinate);
            if(floorCode!=null) properties.put("floorCode", floorCode);

            geometry.put("type", "Polygon");
            List<List<List<Double>>> geoJsonCoordinates = new ArrayList<>(Arrays.asList(cornerCoordinatesToListDouble()));

            geometry.put("coordinates", new JSONArray(geoJsonCoordinates));

            toReturn.put("type", "Feature");
            toReturn.put("properties", properties);
            toReturn.put("geometry", geometry);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return toReturn;
    }

    private List<List<Double>> cornerCoordinatesToListDouble(){
        List<List<Double>> toReturn = new ArrayList<>();

        List<Coordinates> listOfCoordinatesObject = connectedPoints.getListOfCoordinates();

        for(Coordinates coordinates: listOfCoordinatesObject){
            toReturn.add(coordinates.toListDouble());
        }

        return toReturn;
    }
}