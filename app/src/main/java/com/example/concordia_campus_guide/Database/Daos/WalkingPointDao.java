package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Models.WalkingPoint;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WalkingPointDao {
    @Query("SELECT * FROM walkingPoints")
    List<WalkingPoint> getAll();

    @Query("SELECT * FROM walkingPoints WHERE floor_code =:floorCode")
    List<WalkingPoint> getAllWalkingPointsFromFloor(String floorCode);

    @Insert
    void insertAll(List<WalkingPoint> walkingPoints);

    @Delete
    void deleteAll(WalkingPoint... walkingPoints);
}
