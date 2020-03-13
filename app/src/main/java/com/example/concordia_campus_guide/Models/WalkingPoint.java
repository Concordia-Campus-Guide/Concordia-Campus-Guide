package com.example.concordia_campus_guide.Models;

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

    @NonNull
    @ColumnInfo(name = "coordinate")
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


//    public JSONObject getGeoJson(){
//        JSONObject toReturn = new JSONObject();
//        JSONObject properties = new JSONObject();
//        JSONObject geometry = new JSONObject();
//
//        try{
//            if(coordinate!=null) properties.put("StartingPoint", coordinate);
//            if(floorCode!=null) properties.put("floorCode", floorCode);
//
//            geometry.put("type", "Polygon");
//            List<List<List<Double>>> geoJsonCoordinates = new ArrayList<>(Arrays.asList(cornerCoordinatesToListDouble()));
//
//            geometry.put("coordinates", new JSONArray(geoJsonCoordinates));
//
//            toReturn.put("type", "Feature");
//            toReturn.put("properties", properties);
//            toReturn.put("geometry", geometry);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return toReturn;
//    }

//    private List<List<Double>> cornerCoordinatesToListDouble(){
//        List<List<Double>> toReturn = new ArrayList<>();
//
//        List<Coordinates> listOfCoordinatesObject = connectedPoints.getListOfCoordinates();
//
//        for(Coordinates coordinates: listOfCoordinatesObject){
//            toReturn.add(coordinates.toListDouble());
//        }
//
//        return toReturn;
//    }
}