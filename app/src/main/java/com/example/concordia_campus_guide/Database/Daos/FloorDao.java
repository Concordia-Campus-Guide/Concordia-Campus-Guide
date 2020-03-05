package com.example.concordia_campus_guide.Database.Daos;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FloorDao {
    
    @Query("SELECT * FROM floors")
    List<Floor> getAll();

    @Insert
    void insertAll(Floor... floors);

    @Delete
    void deleteAll(Floor... floors);
}
