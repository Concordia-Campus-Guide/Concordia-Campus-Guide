package com.example.concordia_campus_guide.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.concordia_campus_guide.models.PoiType;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.List;

@Dao
public interface WalkingPointDao {
    @Query("SELECT * FROM walkingPoints")
    List<WalkingPoint> getAll();

    @Query("SELECT * FROM walkingPoints WHERE floor_code =:floorCode")
    List<WalkingPoint> getAllWalkingPointsFromFloor(String floorCode);

    @Query("SELECT * FROM walkingPoints WHERE place_code =:placeCode")
    List<WalkingPoint> getAllWalkingPointsFromPlaceCode(String placeCode);

    @Query("SELECT * FROM walkingPoints WHERE  floor_code =:floorCode AND place_code =:placeCode")
    List<WalkingPoint> getAllWalkingPointsFromPlace(String floorCode, String placeCode);

    @Query("SELECT * FROM walkingPoints WHERE floor_code =:floorCode AND point_type=:pointType")
    List<WalkingPoint> getAllAccessPointsOnFloor(String floorCode, @PointType String pointType);

    @Query("SELECT * FROM walkingPoints WHERE point_type=:pointType")
    List<WalkingPoint> getAllPointsForPointType(@PoiType String pointType);

    @Insert
    void insertAll(List<WalkingPoint> walkingPoints);

    @Delete
    void deleteAll(WalkingPoint... walkingPoints);
}
