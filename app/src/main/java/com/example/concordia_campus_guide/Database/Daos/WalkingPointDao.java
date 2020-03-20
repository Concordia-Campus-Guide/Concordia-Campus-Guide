package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Database.Converters.EnumToStringConverter;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
public interface WalkingPointDao {
    @Query("SELECT * FROM walkingPoints")
    List<WalkingPoint> getAll();

    @Query("SELECT * FROM walkingPoints WHERE floor_code =:floorCode")
    List<WalkingPoint> getAllWalkingPointsFromFloor(String floorCode);

    @Query("SELECT * FROM walkingPoints WHERE place_code =:placeCode")
    List<WalkingPoint> getAllWalkingPointsFromPlaceCode(String placeCode);

    @TypeConverters(EnumToStringConverter.class)
    @Query("SELECT * FROM walkingPoints WHERE floor_code =:floorCode AND point_type=:pointType")
    List<WalkingPoint> getAllAccessPointsOnFloor(String floorCode, PointType pointType);

    @Insert
    void insertAll(List<WalkingPoint> walkingPoints);

    @Delete
    void deleteAll(WalkingPoint... walkingPoints);
}
