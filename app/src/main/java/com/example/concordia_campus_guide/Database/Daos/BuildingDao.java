package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Global.User;
import com.example.concordia_campus_guide.Models.Building;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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


}