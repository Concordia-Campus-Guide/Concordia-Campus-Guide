package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Models.Floor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FloorDao {
    
    @Query("SELECT * FROM Floor")
    List<Floor> getAll();

    @Insert
    void insertAll(Floor... floors);
}