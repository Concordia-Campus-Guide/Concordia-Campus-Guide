package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Models.Building;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BuildingDao {

    @Query("SELECT * FROM buildings")
    List<Building> getAll();

    @Query("SELECT * FROM buildings WHERE buildingCode=:buildingCode")
    List<Building> findRepositoriesForUser(final String buildingCode);

    @Insert
    void insertAll(Building... buildings);


}
