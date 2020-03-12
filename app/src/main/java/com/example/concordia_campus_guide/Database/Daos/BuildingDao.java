package com.example.concordia_campus_guide.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Relations.BuildingWithFloors;

import java.util.List;

@Dao
public interface BuildingDao {

    @Query("SELECT * FROM buildings")
    List<Building> getAll();

    @Query("SELECT * FROM buildings WHERE building_code=:buildingCode")
    List<Building> findRepositoriesForUser(String buildingCode);

    @Insert
    void insertAll(List<Building> buildings);

    @Delete
    void deleteAll(Building... buildings);

    @Transaction
    @Query("SELECT * FROM buildings")
    public List<BuildingWithFloors> getBuildingsWithFloors();

    @Query("SELECT * FROM buildings WHERE id=:buildingId")
    public Building getBuildingById(long buildingId);
}