package com.example.concordia_campus_guide.Database.Daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.List;

@Dao
public class WalkingPointDao {

    @Query("SELECT * FROM walkingPoints")
    List<WalkingPoint> getAll();

    @Query("SELECT * FROM buildings WHERE floorCo")
    List<WalkingPoint> findRepositoriesForUser(String buildingCode);

    @Insert
    void insertAll(List<WalkingPoint> walkingPoints);

    @Delete
    void deleteAll(WalkingPoint... walkingPoints);


}
